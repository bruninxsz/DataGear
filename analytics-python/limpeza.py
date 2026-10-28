import math                                                                         # math: funções matemáticas (isfinite)
import re                                                                           # re: expressões regulares (padrões de texto)
import unicodedata                                                                  # unicodedata: remove acentos
from dataclasses import dataclass, field                                            # dataclass: cria classes só de dados com pouco código
from datetime import date, datetime                                                 # date e datetime: datas

import pandas as pd                                                                 # pandas: tabelas de dados (DataFrame)

COLUNAS = [                                                                         # as 10 colunas da planilha
    "codigo_cliente", "nome_cliente", "consultor", "segmento", "nivel_cliente",     # (continuação)
    "faturamento_anual", "servicos_contratados", "status", "data_contratacao", "cidade", "uf",  # (continuação)
]                                                                                   # fecha o bloco
OBRIGATORIAS = COLUNAS

# Chave = texto sem acento, minúsculo e sem pontuação (veja _simples). Valor = nome padronizado.
SEGMENTOS = {                                                                       # dicionário: texto simplificado -> nome oficial do segmento
    "ind": "Indústria", "industria": "Indústria", "industrial": "Indústria",        # Indústria (várias formas de escrever)
    "com": "Comércio", "comercio": "Comércio",                                      # Comércio
    "serv": "Serviços", "servico": "Serviços", "servicos": "Serviços",              # Serviços
    "saude": "Saúde",                                                               # Saúde
    "edu": "Educação", "educacao": "Educação",                                      # Educação
    "tec": "Tecnologia", "ti": "Tecnologia", "tecnologia": "Tecnologia",            # Tecnologia
}                                                                                   # fecha o bloco
SERVICOS = {                                                                        # dicionário: nome simplificado -> nome oficial do serviço
    "internet dedicada": "Internet Dedicada",                                       # Internet Dedicada
    "link empresarial": "Link Empresarial",                                         # Link Empresarial
    "firewall": "Firewall",                                                         # Firewall
    "backup": "Backup",                                                             # Backup
    "mpls": "MPLS",                                                                 # MPLS
    "cloud": "Cloud",                                                               # Cloud
}                            # fecha o bloco

STATUS = {
  "ativo": "Ativo", 
  "inativo": "Inativo",
  "encerrado": "Inativo"
  }                                                # status aceitos

NIVEIS = {"A", "B", "C"}                                                            # níveis aceitos
PARTICULAS = {"de", "da", "do", "das", "dos", "e"}                                  # palavras que ficam em minúsculo no nome


def _simples(texto: str) -> str:                                                    # _simples: tira acento, deixa minúsculo e sem pontuação
    sem_acento = "".join(c for c in unicodedata.normalize("NFKD", str(texto)) if not unicodedata.combining(c))  # separa cada letra do seu acento e descarta o acento
    return re.sub(r"[^a-z0-9]+", " ", sem_acento.lower()).strip()                   # troca símbolos por espaço e tira as pontas


def _vazio(v) -> bool:                                                              # _vazio: true se o valor é nulo ou NaN
    if v is None:                                                                   # None é vazio
        return True                                                                 # então devolve true
    try:                                                                            # tenta perguntar ao pandas
        return bool(pd.isna(v))                                                     # isna detecta NaN e None
    except (TypeError, ValueError):                                                 # se não souber, não é vazio
        return False                                                                # devolve false


def limpar_texto(v) -> str | None:                                                  # limpar_texto: apaga espaços sobrando
    """Remove espaços das pontas e duplicados; vazio vira None."""
    if _vazio(v):                                                                   # vazio
        return None                                                                 # devolve None
    t = re.sub(r"\s+", " ", str(v)).strip()                                         # troca vários espaços por um e corta as pontas
    return t or None                                                                # texto vazio vira None


def _aplicar(serie: pd.Series, funcao) -> pd.Series:                                # _aplicar: aplica uma função a cada item da coluna
    """Como Series.map, mas sempre devolve dtype object e mantém None (o pandas 3 trocaria por NaN)."""
    return pd.Series([funcao(v) for v in serie], index=serie.index, dtype=object)   # cria a nova coluna mantendo None


def titulo(texto: str | None) -> str | None:                                        # titulo: "ANA SOUZA" vira "Ana Souza"
    """'ANA SOUZA' -> 'Ana Souza'; mantém 'de', 'da', 'do'... em minúsculo."""
    if texto is None:                                                               # sem texto
        return None                                                                 # devolve None
    palavras = texto.lower().split(" ")                                             # quebra em palavras minúsculas
    return " ".join(p if (i > 0 and p in PARTICULAS) else p.capitalize() for i, p in enumerate(palavras))  # 'da', 'de'... ficam minúsculas; as outras com inicial maiúscula


def padronizar_segmento(texto: str | None) -> tuple[str | None, bool]:              # padronizar_segmento: devolve (nome oficial, foi reconhecido?)
    """Devolve (segmento padronizado, reconhecido?). Desconhecido vira Título e fica sinalizado."""
    if texto is None:                                                               # sem texto
        return None, False                                                          # devolve (None, falso)
    chave = _simples(texto)                                                         # chave simplificada
    if chave in SEGMENTOS:                                                          # está no dicionário?
        return SEGMENTOS[chave], True                                               # sim: nome oficial e reconhecido
    return titulo(texto), False                                                     # não: devolve em formato Título, marcado como desconhecido


def padronizar_servicos(texto: str | None) -> list[str]:                            # padronizar_servicos: texto "A; B" vira lista de nomes oficiais
    if texto is None:                                                               # sem texto
        return []                                                                   # lista vazia
    vistos: list[str] = []                                                          # lista de serviços já vistos (sem repetir)
    for parte in texto.split(";"):                                                  # para cada pedaço separado por ;
        parte = limpar_texto(parte)                                                 # limpa o pedaço
        if not parte:                                                               # vazio: pula
            continue                                                                # continue = vai para o próximo pedaço
        nome = SERVICOS.get(_simples(parte)) or titulo(parte)                       # nome oficial, ou formato Título se não estiver no dicionário
        if nome not in vistos:                                                      # se ainda não está na lista
            vistos.append(nome)                                                     # acrescenta
    return vistos                                                                   # devolve a lista


def parse_numero(v) -> float | None:                                                # parse_numero: texto vira número (aceita R$ e vírgula)
    """Aceita 1850000, '1850000.50', '1.850.000,50' e 'R$ 1.850.000,50'. Inválido -> None."""
    if _vazio(v):                                                                   # vazio
        return None                                                                 # devolve None
    if isinstance(v, (int, float)):                                                 # já é número?
        return float(v) if math.isfinite(float(v)) else None                        # devolve como float (se for finito)
    t = re.sub(r"\s", "", str(v).replace("R$", "").replace(" ", ""))                # tira "R$" e espaços
    if not re.fullmatch(r"-?[0-9.,]+", t or ""):                                    # se sobrou letra, não é número
        return None                                                                 # devolve None
    if "." in t and "," in t:                                                       # tem ponto e vírgula juntos?
        t = t.replace(".", "").replace(",", ".") if t.rfind(",") > t.rfind(".") else t.replace(",", "")  # o último símbolo é o decimal
    elif "," in t:                                                                  # só vírgula
        t = t.replace(",", "") if t.count(",") > 1 else t.replace(",", ".")         # várias vírgulas = milhar; uma = decimal
    elif "." in t and re.fullmatch(r"-?\d{1,3}(\.\d{3})+", t):                      # só ponto no formato de milhar (1.850.000)
        t = t.replace(".", "")                                                      # tira os pontos
    try:                                                                            # tenta converter
        return float(t)                                                             # devolve o número
    except ValueError:                                                              # não deu
        return None                                                                 # devolve None


def parse_data(v) -> date | None:                                                   # parse_data: texto vira data (aaaa-mm-dd ou dd/mm/aaaa)
    if _vazio(v):                                                                   # vazio
        return None                                                                 # devolve None
    if isinstance(v, (datetime, pd.Timestamp)):                                     # já é data do pandas/Excel?
        return v.date()                                                             # devolve só a data
    if isinstance(v, date):                                                         # já é date?
        return v                                                                    # devolve igual
    t = str(v).strip()                                                              # trata como texto
    m = re.match(r"^(\d{4})-(\d{2})-(\d{2})(?:[T ].*)?$", t)                        # procura o formato aaaa-mm-dd
    try:                                                                            # tenta converter
        if m:                                                                       # achou o formato ISO
            return date(int(m.group(1)), int(m.group(2)), int(m.group(3)))          # monta a data
        return datetime.strptime(t, "%d/%m/%Y").date()                              # senão tenta dd/mm/aaaa
    except ValueError:                                                              # data inexistente
        return None                                                                 # devolve None


@dataclass                                                                          # dataclass: classe só para carregar o resultado
class ResultadoTransformacao:                                                       # resultado do tratamento
    tratado: pd.DataFrame                                                           # clientes tratados (tabela)
    rejeitadas: pd.DataFrame                                                        # linhas recusadas (tabela)
    padronizacoes: dict[str, int] = field(default_factory=dict)                     # quantas padronizações foram feitas
    segmentos_originais: list[dict] = field(default_factory=list)                   # segmentos que foram corrigidos
    avisos: list[str] = field(default_factory=list)                                 # avisos de qualidade


def tratar(bruto: pd.DataFrame) -> ResultadoTransformacao:                          # tratar: função principal da limpeza
    """Recebe as linhas como texto e devolve o DataFrame tratado + as linhas que o ETL recusou."""
    df = bruto.copy().reset_index(drop=True)                                        # cópia da tabela com índice 0,1,2...
    df.columns = [re.sub(r"(?<!^)(?=[A-Z])", "_", str(c)).lower() for c in df.columns]  # codigoCliente (JSON da aula 10) vira codigo_cliente
    for c in COLUNAS:                                                               # para cada coluna esperada
        if c not in df.columns:                                                     # se não veio na tabela
            df[c] = None                                                            # cria vazia
    if "linha" not in df.columns:                                                   # se não há número da linha
        df["linha"] = df.index + 2  # cabeçalho é a linha 1 da planilha
    df = df[["linha"] + COLUNAS].astype(object)                                     # deixa só as colunas que usamos

    # ---- limpeza (str.strip) ----
    brutos = {c: df[c].copy() for c in COLUNAS}                                     # guarda os valores originais (para contar mudanças)
    for c in COLUNAS:                                                               # para cada coluna
        if c not in ("faturamento_anual", "data_contratacao"):                      # menos faturamento e data (tratados depois)
            df[c] = _aplicar(df[c], limpar_texto)                                   # limpa os espaços

    # ---- padronização ----
    df["segmento_original"] = df["segmento"]                                        # guarda o segmento original
    padrao = [padronizar_segmento(v) for v in df["segmento"]]                       # padroniza cada segmento
    df["segmento"] = pd.Series([p[0] for p in padrao], index=df.index, dtype=object)  # coluna segmento com o nome oficial
    desconhecidos_idx = {i for i, (v, (_, ok)) in zip(df.index, zip(df["segmento_original"], padrao)) if v is not None and not ok}  # linhas cujo segmento não foi reconhecido

    df["codigo_cliente"] = _aplicar(df["codigo_cliente"], lambda x: x.upper() if x else x)  # código em maiúsculas
    df["consultor"] = _aplicar(df["consultor"], titulo)                             # consultor em formato Título
    df["nivel_cliente"] = _aplicar(df["nivel_cliente"], lambda x: x.upper() if x else x)  # nível em maiúsculas
    df["uf"] = _aplicar(df["uf"], lambda x: x.upper() if x and len(x) == 2 and x.isalpha() else None)  # UF em maiúsculas (ou None se inválida)
    df["status"] = _aplicar(df["status"], lambda x: STATUS.get(_simples(x)) if x else None)
    df["servicos"] = _aplicar(df["servicos_contratados"], padronizar_servicos)      # serviços viram lista

    # ---- conversão de tipos ----
    df["faturamento_anual"] = _aplicar(df["faturamento_anual"], parse_numero)       # faturamento vira número
    df["data_contratacao"] = _aplicar(df["data_contratacao"], parse_data)           # data vira data

    # ---- validação defensiva (o backend já validou; aqui é uma proteção extra) ----
    motivos: list[list[str]] = [[] for _ in range(len(df))]                         # lista de motivos de recusa de cada linha
    for i, row in df.iterrows():                                                    # para cada linha
        for c in OBRIGATORIAS:                                                      # para cada coluna obrigatória
            vazio = (not row["servicos"]) if c == "servicos_contratados" else _vazio(row[c])  # serviços: lista vazia conta como vazio
            if vazio:                                                               # se está vazio
                motivos[i].append(f"{c}: vazio ou inválido")                        # anota o motivo
        if i in desconhecidos_idx:                                                  # segmento desconhecido?
            motivos[i].append(f"segmento: '{row['segmento_original']}' não reconhecido")  # anota o motivo
        if not _vazio(row["nivel_cliente"]) and row["nivel_cliente"] not in NIVEIS:  # nível fora de A, B, C?
            motivos[i].append(f"nivel_cliente: '{row['nivel_cliente']}' fora de A/B/C")  # anota o motivo
        if not _vazio(row["faturamento_anual"]) and row["faturamento_anual"] < 0:   # faturamento negativo?
            motivos[i].append("faturamento_anual: negativo")                        # anota o motivo
    duplicado = df["codigo_cliente"].notna() & df["codigo_cliente"].duplicated(keep="first")  # código repetido (mantém o primeiro)
    for i in df.index[duplicado]:                                                   # para cada repetido
        motivos[i].append("codigo_cliente: duplicado")                              # anota o motivo

    recusada = pd.Series([bool(m) for m in motivos], index=df.index)                # linhas recusadas = as que têm algum motivo
    rejeitadas = df.loc[recusada, ["linha", "codigo_cliente", "nome_cliente"]].copy()  # tabela das recusadas
    rejeitadas["motivo"] = [" | ".join(motivos[i]) for i in df.index[recusada]]     # coluna com os motivos

    # ---- quantos valores mudaram na padronização (para o relatório de qualidade) ----
    def mudou(coluna: str, original: pd.Series) -> int:                             # mudou: conta quantos valores foram alterados
        pares = zip(original, df[coluna])                                           # compara o original com o tratado
        return sum(1 for a, b in pares if not _vazio(a) and str(a) != ("" if _vazio(b) else str(b)))  # soma as diferenças

    padronizacoes = {                                                               # relatório de qualidade
        "segmento": mudou("segmento", brutos["segmento"]),                          # segmentos corrigidos
        "consultor": mudou("consultor", brutos["consultor"]),                       # consultores corrigidos
        "nivel_cliente": mudou("nivel_cliente", brutos["nivel_cliente"]),           # níveis corrigidos
    }                                                                               # fim do relatório
    contagem = (                                                                    # conta ocorrências de cada segmento original
        df[~recusada][["segmento_original", "segmento"]].dropna().value_counts().reset_index(name="ocorrencias")  # (continuação)
    )                                                                               # (continuação)
    segmentos_originais = [                                                         # lista dos segmentos corrigidos
        {"original": str(r.segmento_original), "padronizado": str(r.segmento), "ocorrencias": int(r.ocorrencias)}  # original, padronizado e quantas vezes
        for r in contagem.sort_values(["segmento", "ocorrencias"], ascending=[True, False]).itertuples()  # ordenado por segmento
    ]                                                                               # fim da lista

    avisos: list[str] = []                                                          # lista de avisos (vazia por enquanto)

    tratado = df[~recusada].copy()                                                  # tratado = só as linhas boas
    tratado["faturamento_anual"] = tratado["faturamento_anual"].astype(float)       # faturamento como número decimal
    tratado["qtd_servicos"] = tratado["servicos"].map(len)                          # quantidade de serviços por cliente
    tratado["data_contratacao"] = pd.to_datetime(tratado["data_contratacao"].astype("datetime64[ns]"))  # data como data/hora do pandas
    tratado["mes"] = tratado["data_contratacao"].dt.strftime("%Y-%m")               # mês no formato aaaa-mm
    tratado = tratado.reset_index(drop=True)[[                                      # organiza as colunas na ordem final
        "linha", "codigo_cliente", "nome_cliente", "consultor", "segmento", "segmento_original", "nivel_cliente",  # (continuação)
        "faturamento_anual", "servicos", "qtd_servicos", "data_contratacao", "mes", "cidade", "uf",  # (continuação)
    ]]                                                                              # fecha o bloco
    return ResultadoTransformacao(tratado, rejeitadas.reset_index(drop=True), padronizacoes, segmentos_originais, avisos)  # devolve o resultado completo
