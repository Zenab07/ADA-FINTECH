package adapter;

import java.util.List;

public interface DataReader<T> {
    List<T> readData();
}
