package geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GeoServicelmplTest {

    GeoService geoService;

    @BeforeEach
    public void setUp() {
        System.out.println("test started");
        geoService = new GeoServiceImpl();
    }

    @AfterEach
    public void finished() {
        System.out.println("test completed");
    }

    @ParameterizedTest
    @MethodSource("sourceIP")
    public void testByIP(String ip, Location expectedLocation) {
        Assertions.assertEquals(expectedLocation.getCountry(),geoService.byIp(ip).getCountry());
    }

    @ParameterizedTest
    @MethodSource("sourcebyCoordinates")
    public void testByCoordinates(double latitude, double longitude,String errMessage) {
        //two way
        Assertions.assertThrows(RuntimeException.class,() -> geoService.byCoordinates(latitude,longitude));
        /*try {
            geoService.byCoordinates(latitude,longitude);
        } catch (RuntimeException e) {
            Assertions.assertEquals(errMessage,e.getMessage());
        }*/
    }

    private Stream<Arguments> sourceIP() {
        return Stream.of(
                arguments("127.0.0.1", new Location(null, null, null, 0)),
                arguments("172.123.12.19", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                arguments("96.121.15.124", new Location("New York", Country.USA, " 10th Avenue", 32)));
    }

    private Stream<Arguments> sourcebyCoordinates() {
        return Stream.of(
                arguments(55.75165,37.61853,"Not implemented"),
                arguments(45.27489,34.0741,"Not implemented"));
    }
}
