package sender;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageSenderImplTest {

    GeoService geoService;
    LocalizationService localizationService;
    Location location;

    @BeforeEach
    public void setUp() {
        System.out.println("test started");
        location = mock(Location.class);
        geoService = mock(GeoService.class);
        localizationService = new LocalizationServiceImpl();
    }

    @BeforeAll
    public void started() {
        System.out.println("started MessageSenderImplTest");
    }

    @AfterEach
    public void finished() {
        System.out.println("\ntest completed");
    }

    @ParameterizedTest
    @MethodSource("parametersSend")
    public void testSend(String ip, String expected) {


        when(geoService.byIp(ip)).thenReturn(location);
        when(location.getCountry()).thenReturn(new GeoServiceImpl().byIp(ip).getCountry());


        MessageSender messageSender = new MessageSenderImpl(geoService,localizationService);

        Map<String,String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER,ip);

        Assertions.assertEquals(expected,messageSender.send(headers));
    }

    @ParameterizedTest
    @MethodSource("parametersSend1")
    public void testSend1(String ip, Location location,String expected) {

        when(geoService.byIp(ip)).thenReturn(location);

        MessageSender messageSender = new MessageSenderImpl(geoService,localizationService);

        Map<String,String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER,ip);

        Assertions.assertEquals(expected,messageSender.send(headers));
    }

     private Stream<Arguments> parametersSend1() {
        String ru = "Добро пожаловать";
        String en = "Welcome";
        return Stream.of(
                arguments("172.123.12.19",new Location("Moscow", Country.RUSSIA, null, 0),ru),
                arguments("172.",new Location("Moscow", Country.RUSSIA, null, 0),ru),
                arguments("96.44.183.149.",new Location("New York", Country.USA, null,  0),en),
                arguments("96.",new Location("New York", Country.USA, null,  0),en)
        );
    }

    private Stream<Arguments> parametersSend() {
        String ru = "Добро пожаловать";
        String en = "Welcome";
        return Stream.of(
                arguments("172.123.12.19",ru),
                arguments("172.",ru),
                arguments("96.44.183.149.",en),
                arguments("96.",en)
                );
    }
}
