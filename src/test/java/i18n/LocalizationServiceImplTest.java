package i18n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LocalizationServiceImplTest {
    LocalizationService localizationService;

    @BeforeEach
    public void setUp() {
        System.out.println("test started");
        localizationService = new LocalizationServiceImpl();
    }

    @AfterEach
    public void finished() {
        System.out.println("test completed");
    }

    @ParameterizedTest
    @MethodSource("sourceLocal")
    public void testLocale(Country country, String expCountry) {
        Assertions.assertEquals(expCountry,localizationService.locale(country));
    }

    private Stream<Arguments> sourceLocal() {
        String ru = "Добро пожаловать";
        String en = "Welcome";
        return Stream.of(
                arguments(Country.RUSSIA,ru),
                arguments(Country.USA,en),
                arguments(Country.BRAZIL,en),
                arguments(Country.GERMANY,en));
    }
}
