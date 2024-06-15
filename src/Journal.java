import java.io.*;
import java.util.ArrayList;
import java.util.List;

// classe do journaling
public class Journal implements Serializable {
    private List<String> entries; // lista de entradas

    public Journal() {
        this.entries = new ArrayList<>();
    }

    // método adicionando uma nova entrada ao journal
    public void addEntry(String entry) {
        entries.add(entry);
    }

    // método pegando todas as entradas do journal
    public List<String> getEntries() {
        return entries;
    }

    // método limpando todas as entradas do journal
    public void clear() {
        entries.clear(); //
    }

    // método salvando journal em um arquivo
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(entries); // escrevendo a lista de entradas no arquivo
        }
    }

    // método carregando o journal de um arquivo
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            entries = (List<String>) in.readObject(); // lendo a lista de entradas do arquivo
        }
    }
}

