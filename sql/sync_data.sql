CREATE OR REPLACE PROCEDURE sync_data()
LANGUAGE plpgsql
AS $$
BEGIN
  -- safety checks
  IF (SELECT COUNT(*) FROM names_import) != 64 THEN
    RAISE EXCEPTION 'names_import has invalid number, aborting sync';
  END IF;

  IF (SELECT COUNT(*) FROM chores_import) != 1792 THEN
    RAISE EXCEPTION 'chores_import has invalid number, aborting sync';
  END IF;

  -- names/users
  DELETE FROM names AS N
  WHERE NOT EXISTS (
    SELECT 1
    FROM names_import AS I
    WHERE I.name = N.name
  );

  DELETE FROM users AS U
  WHERE NOT EXISTS (
    SELECT 1
    FROM names_import AS I
    WHERE I.name = U.fullname
  );

  INSERT INTO names (name)
  SELECT i.name
  FROM names_import AS I
  WHERE NOT EXISTS (
    SELECT 1
    FROM names AS N
    WHERE N.name = I.name
  );

  -- chores
  TRUNCATE chores;

  INSERT INTO chores (name, description, date)
  SELECT name, description, date
  FROM chores_import;

  TRUNCATE names_import, chores_import;
END;
$$;