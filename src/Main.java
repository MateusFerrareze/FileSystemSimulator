public class Main {
    public static void main(String[] args) {
        FileSystemSimulator fs = new FileSystemSimulator();

        // criando diretórios e os arquivos
        fs.createDirectory("/home");
        fs.createDirectory("/home/user");
        fs.createFile("/home/user/arquivo1.txt", "Conteúdo do arquivo 1");
        fs.createFile("/home/user/arquivo2.txt", "Conteúdo do arquivo 2");

        // listando conteúdo do diretório
        System.out.println("Conteúdo de /home/user:");
        fs.listDirectory("/home/user");
        System.out.println();

        // renomeando arquivo
        fs.renameFile("/home/user/arquivo1.txt", "/home/user/novo.txt");

        // após mudança
        System.out.println("Conteúdo de /home/user após renomeação de arquivo1.txt para novo.txt:");
        fs.listDirectory("/home/user");
        System.out.println();

        // copiando arquivo
        fs.copyFile("/home/user/novo.txt", "/home/user/copiado.txt");

        // após mudança
        System.out.println("Conteúdo de /home/user após cópia de novo.txt para copiado.txt:");
        fs.listDirectory("/home/user");
        System.out.println();

        // apagando arquivo
        fs.deleteFile("/home/user/arquivo2.txt");

        // após mudança
        System.out.println("Conteúdo de /home/user após deleção de arquivo2.txt:");
        fs.listDirectory("/home/user");
        System.out.println();

        // renomeando diretório
        fs.renameDirectory("/home/user", "/home/user2");

        // após mudança
        System.out.println("Conteúdo de /home após renomeação de /home/user para /home/user2:");
        fs.listDirectory("/home");
        System.out.println();

        // apagando diretório
        fs.deleteDirectory("/home/user2");

        // após mudança
        System.out.println("Conteúdo de /home após deleção de /home/user2:");
        fs.listDirectory("/home");
        System.out.println();
    }
}
