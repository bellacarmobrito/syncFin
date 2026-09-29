-- SyncFin — dados de demonstração pública.
-- Idempotente: pode ser rodado de novo a qualquer momento para "resetar" a conta demo.
-- A conta demo é identificada pelo e-mail (EMAIL_DEMO em CadastroServelet.java) e é
-- protegida no código contra troca de e-mail/senha e contra exclusão.
--
-- Senha em texto plano (para o README/tela de login): DemoSync2026!
-- O hash abaixo já é o BCrypt dessa senha (formato $2a$, compatível com jBCrypt).

DO $$
DECLARE
    v_id_cliente   INTEGER;
    v_id_conta_cc  INTEGER; -- Bradesco, Conta Corrente
    v_id_conta_inv INTEGER; -- Itaú, Conta Investimento
    v_id_conta_btg INTEGER; -- BTG Pactual, Outros
    v_id_conta_sal INTEGER; -- Sicoob, Conta Salário
BEGIN
    -- Remove a conta demo anterior (e tudo que depende dela, via ON DELETE CASCADE),
    -- para o script poder ser rodado de novo com segurança.
    DELETE FROM T_CLIENTE WHERE EMAIL = 'demo@syncfin.app';

    INSERT INTO T_CLIENTE (NM_CLIENTE, NR_CELULAR, NR_CPF, EMAIL, SENHA, ST_CONTA)
    VALUES (
        'Ana Beatriz Souza',
        '(11) 98888-7777',
        '529.982.247-25',
        'demo@syncfin.app',
        '$2a$12$oZrY77/k907gtAAF4spKZOEy8Ms5ze6Sy2rQrop8DB4Ri1u6c5G72',
        'Ativa'
    )
    RETURNING ID_CLIENTE INTO v_id_cliente;

    INSERT INTO T_ENDERECO (ID_CLIENTE, CEP, LOGRADOURO, NUMERO, BAIRRO, CIDADE, ESTADO)
    VALUES (v_id_cliente, '01310-100', 'Avenida Paulista', 1578, 'Bela Vista', 'São Paulo', 'SP');

    -- Contas bancárias: nomes iguais ao "fullName" da Brasil API, para os logos aparecerem.
    INSERT INTO T_CONTA_BANCARIA (ID_CLIENTE, NM_INSTITUICAO, AGENCIA, NR_CONTA, TIPO_CONTA, SALDO_ATUAL)
    VALUES (v_id_cliente, 'Banco Bradesco S.A.', '8646', '107051', 'Conta Corrente', 8500.00)
    RETURNING ID_CONTA INTO v_id_conta_cc;

    INSERT INTO T_CONTA_BANCARIA (ID_CLIENTE, NM_INSTITUICAO, AGENCIA, NR_CONTA, TIPO_CONTA, SALDO_ATUAL)
    VALUES (v_id_cliente, 'ITAÚ UNIBANCO S.A.', '9854', '78547', 'Conta Investimento', 950000.00)
    RETURNING ID_CONTA INTO v_id_conta_inv;

    INSERT INTO T_CONTA_BANCARIA (ID_CLIENTE, NM_INSTITUICAO, AGENCIA, NR_CONTA, TIPO_CONTA, SALDO_ATUAL)
    VALUES (v_id_cliente, 'Banco BTG Pactual S.A.', '6060', '5656', 'Outros', 5000.00)
    RETURNING ID_CONTA INTO v_id_conta_btg;

    INSERT INTO T_CONTA_BANCARIA (ID_CLIENTE, NM_INSTITUICAO, AGENCIA, NR_CONTA, TIPO_CONTA, SALDO_ATUAL)
    VALUES (v_id_cliente, 'BANCO COOPERATIVO SICOOB S.A. - BANCO SICOOB', '1345', '8945', 'Conta Salário', 6750.00)
    RETURNING ID_CONTA INTO v_id_conta_sal;

    -- Despesas (datas relativas a hoje, para a demo nunca parecer desatualizada)
    INSERT INTO T_DESPESA (ID_CLIENTE, ID_CONTA, VL_DESPESA, CATEGORIA_DESPESA, DT_VENCIMENTO, OB_DESPESA, ST_DESPESA)
    VALUES
        (v_id_cliente, v_id_conta_cc,  2800.00, 'Moradia',                CURRENT_DATE + 6,  'Aluguel',              'Pendente'),
        (v_id_cliente, v_id_conta_cc,   420.50, 'Alimentação',            CURRENT_DATE - 3,  'Supermercado',         'Pago'),
        (v_id_cliente, v_id_conta_cc,   150.00, 'Transporte',             CURRENT_DATE - 10, 'Combustível',          'Pago'),
        (v_id_cliente, v_id_conta_sal,   89.90, 'Assinaturas e Serviços', CURRENT_DATE + 2,  'Streaming',            'Pendente'),
        (v_id_cliente, v_id_conta_cc,   320.00, 'Saúde',                  CURRENT_DATE - 20, 'Plano odontológico',   'Vencida'),
        (v_id_cliente, v_id_conta_sal,  180.00, 'Lazer',                  CURRENT_DATE + 15, 'Cinema e shows',       'Pendente');

    -- Receitas
    INSERT INTO T_RECEITA (ID_CLIENTE, ID_CONTA, VL_RECEITA, CATEGORIA_RECEITA, DT_RECEBIMENTO, OB_RECEITA, ST_RECEITA)
    VALUES
        (v_id_cliente, v_id_conta_sal, 7200.00, 'Salário',       CURRENT_DATE - 4, 'Salário mensal',              'Recebido'),
        (v_id_cliente, v_id_conta_cc,  1250.00, 'Investimentos', CURRENT_DATE - 1, 'Resgate CDI',                 'Recebido'),
        (v_id_cliente, v_id_conta_cc,   800.00, 'Extra',         CURRENT_DATE + 5, 'Freelance',                   'Programada'),
        (v_id_cliente, v_id_conta_sal,  350.00, 'Reembolso',     CURRENT_DATE + 1, 'Reembolso plano de saúde',    'Pendente');

    -- Investimentos (um deles sem vencimento, de propósito, para mostrar a renda variável)
    INSERT INTO T_INVESTIMENTO (ID_CLIENTE, ID_CONTA, TIPO_INVESTIMENTO, VL_INVESTIMENTO, DT_INVESTIMENTO, RECORRENCIA, ST_INVESTIMENTO, RENDIMENTO, DT_VENCIMENTO)
    VALUES
        (v_id_cliente, v_id_conta_inv, 'Tesouro Direto',         4750.00, CURRENT_DATE - 1,   'Único', 'Ativo',     13.00, CURRENT_DATE + 365),
        (v_id_cliente, v_id_conta_inv, 'Fundos de Investimento', 3700.00, CURRENT_DATE - 3,   'Mensal','Ativo',     13.75, CURRENT_DATE + 730),
        (v_id_cliente, v_id_conta_inv, 'Ações',                  2200.00, CURRENT_DATE - 40,  'Único', 'Ativo',     15.20, NULL),
        (v_id_cliente, v_id_conta_inv, 'CDB / RDB',              1500.00, CURRENT_DATE - 200, 'Único', 'Resgatado', 12.50, CURRENT_DATE - 5);
END $$;
