import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// classe do diretório
public class Directory implements Serializable {
    private String name;
    private List<File> files; // lista de arquivos
    private List<Directory> directories; // lista de subdiretórios

    public Directory(String name) {
        this.name = name;
        this.files = new ArrayList<>();
        this.directories = new ArrayList<>();
    }

    // método pegando o nome do diretório
    public String getName() {
        return name;
    }

    // método setando o nome do diretório
    public void setName(String name) {
        this.name = name;
    }

    // método pegando a lista de arquivos dentro do diretório
    public List<File> getFiles() {
        return files;
    }

    // método pegando a lista de subdiretórios dentro do diretório
    public List<Directory> getDirectories() {
        return directories;
    }

    // método adicionando um arquivo ao diretório
    public void addFile(File file) {
        files.add(file);
    }

    // método adicionando um subdiretório ao diretório
    public void addDirectory(Directory directory) {
        directories.add(directory);
    }

    // método removendo um arquivo pelo nome
    public void removeFile(String fileName) {
        files.removeIf(file -> file.getName().equals(fileName));
    }

    // método removendo um subdiretório pelo nome
    public void removeDirectory(String directoryName) {
        directories.removeIf(directory -> directory.getName().equals(directoryName));
    }
}

