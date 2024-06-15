import java.io.*;
import java.util.*;

public class FileSystemSimulator {  // classe do sistema de arquivos
    private Map<String, String> files; // armazenando caminhos e conteúdos dos arquivos
    private Set<String> directories;   // armazenando diretórios
    private Journal journal;            // journal para registrar as operações

    public FileSystemSimulator() {
        files = new HashMap<>();
        directories = new HashSet<>();
        journal = new Journal();
    }

    // método criando um diretório
    public void createDirectory(String path) {
        directories.add(path); //add diretório ao conjunto destes
        journal.addEntry("CREATE_DIRECTORY:" + path); // registrando a operação no journal
    }

    // método criando um arquivo e conteúdo
    public void createFile(String path, String content) {
        files.put(path, content); //add arquivo e seu conteúdo ao mapa destes
        journal.addEntry("CREATE_FILE:" + path + ":" + content);
    }

    // método listando conteúdo de um diretório
    public void listDirectory(String path) {
        // iterando sobre diretórios e imprimindo os que começam com o caminho dado
        for (String dir : directories) {
            if (dir.startsWith(path + "/")) {
                System.out.println("Directory: " + dir);
            }
        }
        // iterando sobre arquivos e imprimindo os que começam com o caminho dado
        for (String file : files.keySet()) {
            if (file.startsWith(path + "/")) {
                System.out.println("File: " + file);
            }
        }
    }

    // método renomeando arquivos
    public void renameFile(String oldPath, String newPath) {
        if (files.containsKey(oldPath)) {
            String content = files.remove(oldPath); // remove antigo e pega seu conteúdo
            files.put(newPath, content); // add arquivo com novo caminho e conteúdo
            journal.addEntry("RENAME_FILE:" + oldPath + ":" + newPath);
        }
    }

    // método renomeando diretórios
    public void renameDirectory(String oldPath, String newPath) {
        Set<String> directoriesToRemove = new HashSet<>();
        Set<String> directoriesToAdd = new HashSet<>();

        // renomeando diretórios que correspondem ao caminho dado
        for (String dir : directories) {
            if (dir.equals(oldPath) || dir.startsWith(oldPath + "/")) {
                String renamedDir = dir.replaceFirst("^" + oldPath, newPath);
                directoriesToRemove.add(dir); // marcando o antigo para remover
                directoriesToAdd.add(renamedDir); // add o diretório novo
            }
        }

        // atualizando diretórios
        directories.removeAll(directoriesToRemove);
        directories.addAll(directoriesToAdd);
        journal.addEntry("RENAME_DIRECTORY:" + oldPath + ":" + newPath); // registrando a operação no journal

        // renomeando arquivos que correspondem ao caminho dado
        Map<String, String> renamedFiles = new HashMap<>();
        for (Map.Entry<String, String> entry : files.entrySet()) {
            String filePath = entry.getKey();
            if (filePath.startsWith(oldPath + "/")) {
                String newFilePath = filePath.replaceFirst("^" + oldPath, newPath);
                renamedFiles.put(newFilePath, entry.getValue());
            }
        }

        // atualizando arquivos
        files.keySet().removeIf(key -> key.startsWith(oldPath + "/"));
        files.putAll(renamedFiles);
    }

    // método copiando arquivos
    public void copyFile(String sourcePath, String destinationPath) {
        if (files.containsKey(sourcePath)) {
            String content = files.get(sourcePath); // pegando conteúdo do arquivo original
            files.put(destinationPath, content); // copiando conteúdo para o novo caminho
            journal.addEntry("COPY_FILE:" + sourcePath + ":" + destinationPath);
        }
    }

    // método salvando o estado do sistema de arquivos no journal
    public void saveToFile(String filename) throws IOException {
        journal.saveToFile(filename); // salvando operações journal nos arquivos
    }

    // método carregando estados anteriores do sistema de arquivos a partir do journal
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        journal.loadFromFile(filename); // carregando operações antigas de um arquivo

        // reinicializando a estrutura do sistema
        for (String entry : journal.getEntries()) {
            String[] parts = entry.split(":");
            String action = parts[0];
            String path = parts[1];

            switch (action) {
                case "CREATE_DIRECTORY":
                    directories.add(path); // add diretorio criado
                    break;
                case "CREATE_FILE":
                    String content = parts[2];
                    files.put(path, content); // add arquivo e seu conteúdo
                    break;
                case "RENAME_FILE":
                    String oldPath = path;
                    String newPath = parts[2];
                    if (files.containsKey(oldPath)) {
                        String fileContent = files.remove(oldPath); // remove arquivos antigos
                        files.put(newPath, fileContent); // add arquivos novos e caminhos
                    }
                    break;
                case "RENAME_DIRECTORY": // renomeando diretórios
                    String oldDirPath = path;
                    String newDirPath = parts[2];
                    Set<String> updatedDirs = new HashSet<>();
                    for (String dir : directories) {
                        if (dir.equals(oldDirPath) || dir.startsWith(oldDirPath + "/")) {
                            String renamedDir = dir.replaceFirst("^" + oldDirPath, newDirPath);
                            updatedDirs.add(renamedDir);
                        } else {
                            updatedDirs.add(dir);
                        }
                    }
                    directories = updatedDirs; // atualizando novos diretórios(renomeados)
                    break;
                case "COPY_FILE":
                    String sourcePath = path;
                    String destPath = parts[2];
                    if (files.containsKey(sourcePath)) {
                        String fileContent = files.get(sourcePath); // pega conteúdos dos arquivos originais
                        files.put(destPath, fileContent); // copiando para novo caminho
                    }
                    break;
                default:
                    System.out.println("Ação não reconhecida: " + action);
                    break;
            }
        }
    }

    // método apagando arquivos
    public void deleteFile(String path) {
        files.remove(path); // removendo
        journal.addEntry("DELETE_FILE:" + path);
    }

    // método deletando diretórios e seus arquivos contidos
    public void deleteDirectory(String path) {
        Set<String> filesToRemove = new HashSet<>();
        for (String file : files.keySet()) {
            if (file.startsWith(path + "/")) {
                filesToRemove.add(file); // marcando arquivos para remover
                journal.addEntry("DELETE_FILE:" + file);
            }
        }
        files.keySet().removeAll(filesToRemove); // removendo todos os arquivos

        Set<String> directoriesToRemove = new HashSet<>();
        for (String dir : directories) {
            if (dir.equals(path) || dir.startsWith(path + "/")) {
                directoriesToRemove.add(dir); // marcando diretório para remover
                journal.addEntry("DELETE_DIRECTORY:" + dir);
            }
        }
        directories.removeAll(directoriesToRemove); // removendo todos os diretórios
    }
}

