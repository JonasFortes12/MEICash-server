INSERT INTO users (id, email, username, password, first_name, last_name, company_name)
VALUES
    ('b2d13c5a-3df0-4673-b3e6-49244f395ac8', 'almeida@example.com', 'AlmeidaCasiono12', 'senha123', 'Almeida', 'Casiono', 'Barbearia Chicote')
ON CONFLICT (id) DO NOTHING;

INSERT INTO transactions (id, timestamp, type, category, value, description)
VALUES ('t7d1334a-3df0-4673-b3e6-49244f45rrc8', '2024-06-01 12:00:00', 'INCOME', 'BUY', 100.00, 'Venda do produto X')
ON CONFLICT (id) DO NOTHING;
