INSERT INTO users (id, username, password) VALUES
    ('a0000000-0000-0000-0000-000000000001', 'alice', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi'),
    ('a0000000-0000-0000-0000-000000000002', 'bob',   '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi')
ON CONFLICT DO NOTHING;
