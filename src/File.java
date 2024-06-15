import java.io.Serializable;

// classe dos arquivos
public class File implements Serializable {
    private String name;
    private String content;

    public File(String name, String content) {
        this.name = name;
        this.content = content;
    }

    // método pegando o nome do arquivo
    public String getName() {
        return name;
    }

    // método setando o nome do arquivo
    public void setName(String name) {
        this.name = name;
    }

    // método pegando o conteúdo do arquivo
    public String getContent() {
        return content;
    }

    // método setando o conteúdo do arquivo
    public void setContent(String content) {
        this.content = content; // Define um novo conteúdo para o arquivo
    }
}

