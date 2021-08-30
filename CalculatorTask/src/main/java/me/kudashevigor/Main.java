package me.kudashevigor;

import me.kudashevigor.exceptions.AbstractException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Разработчик: Кудашев Игорь\n");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пример для вычисления:");
        String input = scanner.nextLine();
        try {
            System.out.printf("Результат вычислений: %s%n", OperationTypes.calculate(input));
        } catch (AbstractException ae) {
            System.out.println(ae.getMessage());
        }
    }

}
