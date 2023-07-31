import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;
public class ToyGame {
    private PriorityQueue<Toy> toyQueue;

    public ToyGame() {
        toyQueue = new PriorityQueue<>((t1, t2) -> t2.getWeight() - t1.getWeight());
    }

    public void addToy(Toy toy) {
        toyQueue.add(toy);
    }

    public void updateWeight(int id, int weight) {
        for (Toy toy : toyQueue) {
            if (toy.getId() == id) {
                toy.setWeight(weight);
                break;
            }
        }
    }

    public Toy getPrizeToy() {
        Toy prizeToy = toyQueue.poll();
        if (prizeToy != null) {
            prizeToy.setQuantity(prizeToy.getQuantity() - 1);
            System.out.println("Получена призовая игрушка:");
            System.out.println("ID: " + prizeToy.getId() + ", Название: " + prizeToy.getName() + ", Количество: " + prizeToy.getQuantity() + ", Вес: " + prizeToy.getWeight());
        }
        return prizeToy;
    }

    public void playGame() {
        Random random = new Random();
        int totalToys = toyQueue.size();
        int oneWeight = totalToys / 5;
        int twoWeight = totalToys / 5;
        
        try (FileWriter fileWriter = new FileWriter("prize_toys.txt")) {
            for (int i = 0; i < 10; i++) {
                int weight = random.nextInt(totalToys);

                Toy toy = getPrizeToy();
                if (toy != null) {
                    if (weight < oneWeight) {
                        fileWriter.write("ID: " + toy.getId() + ", " + toy.getName() + "\n");
                    } else if (weight < oneWeight + twoWeight) {
                        fileWriter.write("ID: " + toy.getId() + ", " + toy.getName() + "\n");
                    } else {
                        fileWriter.write("ID: " + toy.getId() + ", " + toy.getName() + "\n");
                    }

                    if (toy.getQuantity() > 0) {
                        toy.setWeight(weight);
                        toyQueue.add(toy);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printToyQueue() {
        System.out.println("Очередь игрушек, ожидающих выдачи:");
        for (Toy toy : toyQueue) {
            System.out.println("ID: " + toy.getId() + ", Название: " + toy.getName() + ", Количество: " + toy.getQuantity() + ", Вес: " + toy.getWeight());
        }
    }

    public static void main(String[] args) {
        ToyGame toyGame = new ToyGame();

        // Добавление новых игрушек
        toyGame.addToy(new Toy(1, "Конструктор", 5, 20));
        toyGame.addToy(new Toy(2, "Робот", 3, 20));
        toyGame.addToy(new Toy(3, "Кукла", 10, 60));

        // Изменение веса игрушки
        toyGame.updateWeight(1, 30);

        // Вывод очереди игрушек
        toyGame.printToyQueue();

        // Розыгрыш игрушек
        toyGame.playGame();
    }
}