import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.CalculadoraEstacionamento;

public class CalculadoraEstacionamentoTest {

    @ParameterizedTest
    @CsvSource({
        // Comum ou VIP 15 minutos (Cortesia)
        "'2024-10-10T10:00', '2024-10-10T10:15', false, 0.0",
        "'2024-10-10T10:00', '2024-10-10T10:15', true, 0.0",
        // Comum maior que 15 minutos
        "'2024-10-10T10:00', '2024-10-10T10:16', false, 5.90",
        // VIP maior que 15 minutos
        "'2024-10-10T10:00', '2024-10-10T10:16', true, 2.95",
        // Comum igual a 1 hora
        "'2024-10-10T09:00', '2024-10-10T10:00', false, 5.90",
        // VIP igual a 1 hora
        "'2024-10-10T09:00', '2024-10-10T10:00', true, 2.95",
        // Comum 1 hora e 1 minuto
        "'2024-10-10T09:00', '2024-10-10T10:01', false, 8.40",
        // VIP 1 hora e 1 minuto
        "'2024-10-10T09:00', '2024-10-10T10:01', true, 4.20",
        // Comum pernoite
        "'2024-10-10T08:00', '2024-10-11T09:00', false, 50.00",
        // VIP pernoite
        "'2024-10-10T08:00', '2024-10-11T09:00', true, 25.00"
    })
    public void testCalculoEstacionamento(String entradaStr, String saidaStr, boolean isVip, double valorEsperado) {
        LocalDateTime entrada = LocalDateTime.parse(entradaStr);
        LocalDateTime saida = LocalDateTime.parse(saidaStr);

        double valorCalculado = CalculadoraEstacionamento.calcularValor(entrada, saida, isVip);

        assertEquals(valorEsperado, valorCalculado);
    }

    @ParameterizedTest
    @CsvSource({
        // Comum ou VIP entrada inválida
        "'2024-10-10T07:00', '2024-10-10T17:00', false",
        "'2024-10-10T07:00', '2024-10-10T17:00', true",
        // Comum ou VIP saída inválida
        "'2024-10-10T08:00', '2024-10-11T07:00', false",
        "'2024-10-10T08:00', '2024-10-11T07:00', true"
    })
    public void testEntradaOuSaidaInvalida(String entradaStr, String saidaStr, boolean isVip) {
        LocalDateTime entrada = LocalDateTime.parse(entradaStr);
        LocalDateTime saida = LocalDateTime.parse(saidaStr);

        assertThrows(IllegalArgumentException.class, () -> {
            CalculadoraEstacionamento.calcularValor(entrada, saida, isVip);
        });
    }
}
