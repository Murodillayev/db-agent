-- Rol yaratish
CREATE ROLE project_select;

-- Huquqlar: faqat SELECT
GRANT CONNECT ON DATABASE test TO project_select;
GRANT USAGE ON SCHEMA public TO project_select;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO project_select;

-- Kelajakdagi jadvallar uchun avtomatik SELECT
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT ON TABLES TO project_select;


-- Rol yaratish
CREATE ROLE project_cru;

-- Huquqlar: CONNECT, USAGE, SELECT, INSERT, UPDATE
GRANT CONNECT ON DATABASE test TO project_cru;
GRANT USAGE ON SCHEMA public TO project_cru;

-- Hozirgi jadvallar uchun
GRANT SELECT, INSERT, UPDATE ON ALL TABLES IN SCHEMA public TO project_cru;

-- Kelajakdagi jadvallar uchun
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE ON TABLES TO project_cru;



-- Rol yaratish
CREATE ROLE project_crud;

-- Huquqlar: to'liq CRUD
GRANT CONNECT ON DATABASE test TO project_crud;
GRANT USAGE ON SCHEMA public TO project_crud;

-- Hozirgi jadvallar uchun
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO project_crud;

-- Kelajakdagi jadvallar uchun
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO project_crud;