INSERT INTO users
  (id, login_id, password, name, role, version, created_at, updated_at)
VALUES
   (1, 'staff', 'pass', 'スタッフ', 0, 1, current_timestamp, current_timestamp)
  ,(2, 'admin', 'pass', '管理者', 1, 1, current_timestamp, current_timestamp)
;
