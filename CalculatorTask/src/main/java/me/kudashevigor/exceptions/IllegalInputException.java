package me.kudashevigor.exceptions;

import me.kudashevigor.OperationTypes;

import java.util.Arrays;

public class IllegalInputException extends AbstractException {

    public IllegalInputException(String input) {
        super(String.format(
                        """
                        Недопустимый ввод "%s", проверьте правильность введенных значений
                        Допустимый формат - a ? b, где a и b - числа одного типа, а ? один из допустимых операторов
                        Разрешенные типы операций - %s,
                        Разрешенные числа - арабские или римские в пределах 1-10 (I-X)
                        При использовании римских чисел результат должен быть больше 0
                        """,
                input, Arrays.toString(OperationTypes.getStringValues())), "4");
    }

}
