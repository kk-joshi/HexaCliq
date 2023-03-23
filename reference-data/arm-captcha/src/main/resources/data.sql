insert into customer(id, username, password, role, full_name, email_id, dob) 
values (100l, 'pratik', '$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e', 'ADMIN', 'pratik thakur', 'pratik.thakur007@gmail.com', now());


insert into CAPTCHA_QUESTION (id, user_id, attempt, answered_correct, question, answer, created_on) 
values (100l, 100l, 0, false, '100 * 0', 0, '');

insert into Wallet(id, user_id, current_balance, multiplier_Factor, total_Widrawn_Amount, min_Widrawn_Amount)
values (100l, 100l, 0, 0.1, 0, 500);
