create TABLE [my_schema].[Users] (
	id int identity(1,1),
	firstname varchar(50) not null,
	middlename varchar(50) null,
	lastname varchar(50) not null,
	birthdate date NOT NULL,
	created_at datetime2 NOT null,
	updated_at datetime2 NOT NULL,
	
	constraint PK_Users primary key(id),
);

CREATE TABLE [my_schema].[UserRole] (
	id int identity(1,1),
	user_id int NOT NULL,
	role_name varchar(30) NOT NULL,
	
	CONSTRAINT PK_UserRole PRIMARY KEY(id),
	CONSTRAINT FK_UserRole_UserID FOREIGN KEY (user_id) REFERENCES [my_schema].[Users](id),
	CONSTRAINT CK_UserRole_RoleName CHECK (role_name IN ('student', 'teacher'))
);

CREATE TABLE [my_schema].[Address] (
	id int identity(1,1),
	user_id int NOT NULL,
	line_1 varchar NOT null,
	line_2 varchar NULL,
	barangay varchar NOT NULL,
	city varchar NOT NULL,
	province varchar NOT NULL,
	postal_code int NOT NULL,
	
	CONSTRAINT PK_Address PRIMARY KEY(id),
	CONSTRAINT FK_Address_UserID FOREIGN KEY(user_id) REFERENCES [my_schema].[Users](id)
);

CREATE TABLE [my_schema].[Student] (
	id int IDENTITY(1,1),
	user_id int NOT NULL,
	student_number varchar(11) NOT NULL,
	
	CONSTRAINT PK_Student PRIMARY KEY(id),
	CONSTRAINT UQ_Student_UserID UNIQUE(user_id),
	CONSTRAINT UQ_Student_StudentNumber UNIQUE(student_number),
	CONSTRAINT FK_Student_UserID FOREIGN KEY(user_id) REFERENCES [my_schema].[Users](id)
);

CREATE TABLE [my_schema].[Teacher] (
	id int IDENTITY(1,1),
	user_id int NOT NULL,
	teacher_number varchar(11),
	
	CONSTRAINT PK_Teacher PRIMARY KEY(id),
	CONSTRAINT UQ_Teacher_UserID UNIQUE(user_id),
	CONSTRAINT UQ_Teacher_TeacherNumber UNIQUE(teacher_number),
	CONSTRAINT FK_Teacher_UserID FOREIGN KEY(user_id) REFERENCES [my_schema].[Users](id)
);
