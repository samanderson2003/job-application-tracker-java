-- Initialize demo user for H2 database
INSERT INTO users (username, password, email, full_name, phone, created_at, updated_at) 
VALUES ('demo', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'demo@jobtracker.com', 'Demo User', '555-0100', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Add sample job applications
INSERT INTO jobs (user_id, company, position, location, salary_range, job_description, status, application_date, created_at, updated_at)
VALUES 
(1, 'Google', 'Software Engineer', 'Mountain View, CA', '$120k - $180k', 'Backend development with Go and Python', 'APPLIED', CURRENT_DATE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Microsoft', 'Senior Developer', 'Redmond, WA', '$140k - $200k', 'Azure cloud services development', 'INTERVIEW', CURRENT_DATE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Amazon', 'Full Stack Developer', 'Seattle, WA', '$130k - $190k', 'E-commerce platform development', 'WISHLIST', CURRENT_DATE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
