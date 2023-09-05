-- stored_procedures.sql

-- Define a Stored Procedure para inserir um usuário
CREATE PROCEDURE InsertUser (
    @name TEXT,
    @username TEXT,
    @password TEXT,
    @email TEXT,
    @birthdate TEXT,
    @sex TEXT,
    @type TEXT,
    @cpf_cnpj TEXT,
    @photoUrl TEXT,
    @address TEXT
)
AS
BEGIN
    INSERT INTO users (name, username, password, email, birthdate, sex, type, cpf_cnpj, photoUrl, address)
    VALUES (@name, @username, @password, @email, @birthdate, @sex, @type, @cpf_cnpj, @photoUrl, @address);
END;

-- Define uma Stored Procedure para atualizar um usuário
CREATE PROCEDURE UpdateUser (
    @id INTEGER,
    @name TEXT,
    @username TEXT,
    @password TEXT,
    @email TEXT,
    @birthdate TEXT,
    @sex TEXT,
    @type TEXT,
    @photoUrl TEXT,
    @cpf_cnpj TEXT
)
AS
BEGIN
    UPDATE users
    SET
        name = @name,
        username = @username,
        password = @password,
        email = @email,
        birthdate = @birthdate,
        sex = @sex,
        type = @type,
        photoUrl = @photoUrl,
        cpf_cnpj = @cpf_cnpj
    WHERE
        id = @id;
END;

-- Define uma Stored Procedure para deletar um usuário
CREATE PROCEDURE DeleteUser (
    @id INTEGER
)
AS
BEGIN
    DELETE FROM users
    WHERE
        id = @id;
END;