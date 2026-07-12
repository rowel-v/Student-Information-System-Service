ALTER TABLE [my_schema].[Student]
ADD CONSTRAINT CK_Student_StudentNumber_Length
CHECK (LEN(student_number) = 11);
