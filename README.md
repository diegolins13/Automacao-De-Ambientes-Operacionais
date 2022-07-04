# Automacao-De-Ambientes-Operacionais

<i>Um dos principais desafios para um bom gerenciamento de infraestrutura, é implementar
automação para permitir produtividade aos times de administração de tecnologia, bem como,
minimizar ações humanas nos ambientes dos clientes.

O cliente “Acme Co.” possui um servidor centralizado de backup, o qual recebe arquivos de todos
os demais servidores, move os dados para um volume temporário, para que deste volume os dados
sejam copiados por uma ferramenta de backup externa.

De forma a minimizar o nível de intervenção neste ambiente, foi implementado um script em java,
para automatizar as seguintes ações:</i>
<b>
- Listar todos arquivos (nome, tamanho, data de criação, data da última modificação) localizados no
caminho /home/valcann/backupsFrom;

- Salvar o resultado no arquivo backupsFrom.log em /home/valcann/;

- Remover todos os arquivos com data de criação superior a 3 (três) dias;

- Copiar todos os arquivos os arquivos com data de criação menor ou igual a 3 (três) dias em
homevalcann/backupsTo;

- Salvar o resultado no arquivo backupsTo.log em /home/valcann/.
Fique a vontade para pesquisar ou reutilizar códigos disponíveis na comunidade.
