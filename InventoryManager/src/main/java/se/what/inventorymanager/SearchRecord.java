package se.what.inventorymanager;
import jakarta.persistence.*;

@Entity
@Table(name = "search_record")
public class SearchRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String query;

    public SearchRecord(String query) {
        this.query = query;
    }

    public SearchRecord() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "SearchRecord{" +
                "id=" + id +
                ", query='" + query + '\'' +
                '}';
    }
}
