INSERT INTO users (id, email, username, password, first_name, last_name, company_name)
VALUES
    ('b2d13c5a-3df0-4673-b3e6-49244f395ac8', 'almeida@example.com', 'AlmeidaCasiono12', 'senha123', 'Almeida', 'Casiono', 'Barbearia Chicote')
ON CONFLICT (id) DO NOTHING;

INSERT INTO categories (id, name, color, user_id)
VALUES ('t7a1334a-3df0-4673-89f7-142556d5bbt9', 'red', 'promoção de lançamento', 'b2d13c5a-3df0-4673-b3e6-49244f395ac8')
ON CONFLICT (id) DO NOTHING;

INSERT INTO transactions (id, title, timestamp, type, value, description, user_id, category_id)
VALUES ('t7d1334a-3df0-4673-b3e6-49244f45rrc8','Venda do produto X' ,'2024-06-01 12:00:00', 'INCOME', 100.00, 'Venda do produto X para a ana', 'b2d13c5a-3df0-4673-b3e6-49244f395ac8', 't7a1334a-3df0-4673-89f7-142556d5bbt9')
ON CONFLICT (id) DO NOTHING;