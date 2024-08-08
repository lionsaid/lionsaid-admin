package com.lionsaid.admin.web.utils;

import com.alibaba.fastjson2.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpMethod.*;

/**
 * The type Lion dance http utils.
 *
 * @author sunwei
 */
@Builder
@Slf4j
public final class LionSaidHttpUtils {

    public static OkHttpClient client = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES) // 设置连接超时时间为30秒
            .readTimeout(3, TimeUnit.MINUTES).build();

    public static void main(String[] args) {
        log.error("{}", "gg");
        ArrayList<@Nullable String> list = Lists.newArrayList();
        // list.add("CSG2404573");
        list.add("DSG2407441");
        list.add("DSS2400151");
        list.add("GSS2400159");
        list.add("HEG2407505");
        list.add("HEG2407511");
        list.add("HEG2407583");
        list.add("HES2400454");
        list.add("HES2400464");
        list.add("MAO2200002");
        list.add("MAO2200003");
        list.add("MAO2200007");
        list.add("MAO2200008");
        list.add("MAO2200010");
        list.add("MAO2200014");
        list.add("MAO2300014");
        list.add("MAO2300018");
        list.add("MAO2300019");
        list.add("MAO2300020");
        list.add("MAO2300021");
        list.add("MAO2300022");
        list.add("MAO2300023");
        list.add("MAO2300024");
        list.add("MAO2300025");
        list.add("MAO2300026");
        list.add("MAO2300027");
        list.add("MAO2300028");
        list.add("MAO2400001");
        list.add("MAO2400002");
        list.add("MAO2400009");
        list.add("MAO2400015");

        list.forEach(o -> initMeetingSignQR(o));
        list.forEach(o -> initMeetingPoster(o));

    }


    @SneakyThrows
    public static void messages1(String aaaa) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, aaaa);
        Request request = new Request.Builder()
                .url("https://azrzanjancnidataprod.servicebus.chinacloudapi.cn/queue_ams_meeting/messages?api-version=2015-01")
                .method("POST", body)
                .addHeader("Accept", "text/plain, */*; q=0.01")
                .addHeader("Referer", "")
                .addHeader("BrokerProperties", "{}")
                .addHeader("Authorization", "SharedAccessSignature sr=https%3A%2F%2Fazrzanjancnidataprod.servicebus.chinacloudapi.cn%2F&sig=%2BRPxjvwQBj57SDUVJug4B5DsNGfsjfvihokJshCUgho%3D&se=1698029368.225&skn=RootManageSharedAccessKey")
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36")
                .addHeader("Content-Type", "text/plain")
                .build();
        Response response = client.newCall(request).execute();
    }

    @SneakyThrows
    public static void sendMessage(String args) {

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("connectionString", "Endpoint=sb://azrzanjancnidataprod.servicebus.chinacloudapi.cn/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=bJmjSUGEnnbNMtelx3B3387ehTOndSkPvgAdmYBU3+w=")
                .addFormDataPart("queueName", "queue_iconnect_visit_array")
                .addFormDataPart("body", args)
                .build();
        Request request = new Request.Builder()
                .url("https://jbrainprod-gateway.myxjp.com/API-XJP/pharmbrain/azureServiceBusMessage/sendMessage")
                .method("POST", body)
                .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHTUx3MWUxSGo4VVZkY0gtNVJPeDdUdld1T01vT1N2cURESU5pQ3MxbG9FIn0.eyJleHAiOjE2OTE3NDc4OTksImlhdCI6MTY4OTE1NTg5OSwianRpIjoiN2I5N2FkMGQtNjQyNi00OTZjLWFhYmItYTA3ZGFmNDRkZjgzIiwiaXNzIjoiaHR0cHM6Ly9qYnJhaW5wcm9kLm15eGpwLmNvbS9hdXRoL3JlYWxtcy9TVEVQIiwic3ViIjoiZTZmY2QzOWMtYTEyYy00YjM4LWE2YjEtNjZlMjFiZmViNmMwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHVibGljLWxvZ2luLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiJjZTlmNzc5ZS1kNjViLTQ2MjYtYTIyNi1kOWMyZTEyYjVlYzgiLCJzY29wZSI6InByb2ZpbGUiLCJzaWQiOiJjZTlmNzc5ZS1kNjViLTQ2MjYtYTIyNi1kOWMyZTEyYjVlYzgiLCJuYW1lIjoi5ZGo55CoIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiNzAyNDI0NDAyIiwiZ2l2ZW5fbmFtZSI6IuWRqOeQqCJ9.KYFVGD8SQCzu2n6kr0sFrK5z0fzlMCLwKHjkVVvY6xQ6u0I0Wki1JnifnywzCIK45oA-XLCQJCW1Q-dSFZSXs5zc000fXFNZ62ztrV_vg-YrPogNVZRuYpROAMj1ZCQYVG4A6dltjth8FoiTXEyRz0q1rchvaq0v1AOYdxwJo8fFjPUOovLP7exIdtjOlVJoaoakeXozdphN-Q21OyTVLwze-x0lAMe8omWCp-IZ1DGSMH0WzP4miqOfyGtAGuXWHWSISKJXQKfisrWa-Z3e-1ivVPIYfcv1AMeBIHljnQt0mvgD7UYR-6o9Np4THiG7jEGjwBE2LPjafH4Q6UWb3g")
                .build();
        Response response = client.newCall(request).execute();
        log.info("======{}", response);
    }

    @SneakyThrows
    public static void deadLetter(String args) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder().get()
                .url("https://azrzanjancnidataprod.servicebus.chinacloudapi.cn/queue_iconnect_visit_array/$DeadLetterQueue/messages?timeout=5&PreviousSequenceNumber=" + args + "&api-version=2015-01&_=1695945372806")
                .addHeader("Accept", "text/plain, */*; q=0.01")
                .addHeader("Referer", "")
                .addHeader("Authorization", "SharedAccessSignature sr=https%3A%2F%2Fazrzanjancnidataprod.servicebus.chinacloudapi.cn%2F&sig=n9pXZM0%2FxEETwPWb%2FDQdAl9Umn04ITTLENU4Bsbftkw%3D&se=1695953091.244&skn=RootManageSharedAccessKey")
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    @SneakyThrows
    public static void messages(String args) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, args);
        Request request = new Request.Builder()
                .url("https://azrzanjancnidataprod.servicebus.chinacloudapi.cn/queue_iconnect_visit_array/messages?api-version=2015-01")
                .method("POST", body)
                .addHeader("Accept", "text/plain, */*; q=0.01")
                .addHeader("Referer", "")
                .addHeader("BrokerProperties", "{}")
                .addHeader("Authorization", "SharedAccessSignature sr=https%3A%2F%2Fazrzanjancnidataprod.servicebus.chinacloudapi.cn%2F&sig=n9pXZM0%2FxEETwPWb%2FDQdAl9Umn04ITTLENU4Bsbftkw%3D&se=1695953091.244&skn=RootManageSharedAccessKey")
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(">>>>>>>>>>>>>>>" + response.body().string());
    }

    @SneakyThrows
    public static void deleteMeetingPhoto(String args) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder().get()
                .url("https://jbrainprod-gateway.myxjp.com/API-XJP/bizconf/deleteMeetingPhoto?id=" + args)
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:124.0) Gecko/20100101 Firefox/124.0")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("loginSys", "idatapharm")
                .addHeader("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHTUx3MWUxSGo4VVZkY0gtNVJPeDdUdld1T01vT1N2cURESU5pQ3MxbG9FIn0.eyJleHAiOjE3MTgxNjA0NzMsImlhdCI6MTcxNTU2ODQ3MywianRpIjoiOWQ5YzU5NGItN2UyYi00MDQxLWFiY2MtZDdiNjkzNjZkYzE5IiwiaXNzIjoiaHR0cHM6Ly9qYnJhaW5wcm9kLm15eGpwLmNvbS9hdXRoL3JlYWxtcy9TVEVQIiwic3ViIjoiMTVhZDE1OTAtNDNiNS00MTcxLTkyNTgtZWRhYWI3ZjhjM2Y0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHVibGljLWxvZ2luLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiI1NjFiYTExNi02MTRhLTRkYzctYmJlYy0zNzIxODAxYmZhMWMiLCJzY29wZSI6InByb2ZpbGUiLCJzaWQiOiI1NjFiYTExNi02MTRhLTRkYzctYmJlYy0zNzIxODAxYmZhMWMiLCJuYW1lIjoi6ams5a6P6JW-IiwicHJlZmVycmVkX3VzZXJuYW1lIjoiMTUyOTY0MDE5IiwiZ2l2ZW5fbmFtZSI6IumprOWuj-iVviJ9.NwgDNwGzWnHU325YSsB445rhcni5HJtz6rMmcGXFsNVTEAM8YZa-EJucZ_XhOfk5mq6qAqrdu0trpPDfmzbhKPP9PZ8wIogxYEe-n1YJd2ee64YngiAGNvGon-HUN61_p0GPYRDGarwJE4-DsmkMcaM8KO9hpIFh5oIA2JRbbdmRc0Y-T1mSm7q3MB9NTNF29f4Y4LsWZyDnANmDF3VhrlDXnVhELTn6wx2m_4v_NDhLGgoyqFwoMqLEloPlR4nZ4twoku1cPv34O89J0GUIiOyf6mK09kpzOpLKPbeTQKQtV7MtNwFQvDGV1U6F1Ewe69JLZfOzEu3TGiIOFpsb-w")
                .addHeader("system", "pc")
                .addHeader("language", "zh")
                .addHeader("Origin", "https://jbrainprod.myxjp.com")
                .addHeader("DNT", "1")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "https://jbrainprod.myxjp.com/")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-site")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(">>>>>>>>>>>>>>>" + response.body().string());
    }

    @SneakyThrows
    public static void repairMeeting(String args) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()

                    .build();
            Request request = new Request.Builder()
                    .url("https://jbrainprod-gateway.myxjp.com/API-XJP/pharmbrain/meeting/repairMeeting?meetingNo=" + args + "&sourceName=ams")
                    .addHeader("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHTUx3MWUxSGo4VVZkY0gtNVJPeDdUdld1T01vT1N2cURESU5pQ3MxbG9FIn0.eyJleHAiOjE3MjE4OTU1NjIsImlhdCI6MTcxOTMwMzU2MiwianRpIjoiMmQ4YWZkZTktZGFkMy00OTI3LTlhMTMtMzBmYmMzYmFhNzE1IiwiaXNzIjoiaHR0cHM6Ly9qYnJhaW5wcm9kLm15eGpwLmNvbS9hdXRoL3JlYWxtcy9TVEVQIiwic3ViIjoiYjM4NzBjNDItOTQwNS00NjQ0LWEzNTItZWQ2ZDY5ODZiZDg5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHVibGljLWxvZ2luLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiI5OGU1ZjdjOC02NTMxLTQ0YWYtODM1Mi0yNzM2ZjdkMTQ0YTQiLCJzY29wZSI6InByb2ZpbGUiLCJzaWQiOiI5OGU1ZjdjOC02NTMxLTQ0YWYtODM1Mi0yNzM2ZjdkMTQ0YTQiLCJuYW1lIjoiamNyZHRlc3QiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiI3MDI0MDg2NTgiLCJnaXZlbl9uYW1lIjoiamNyZHRlc3QifQ.aLAeKYYAn96nK9_E3Ya43HOtYo1BsKElNQL5l7z-SA5qE6EfO__zqqbutX_7A4tgL-n0u5YZgK-Y_HNwM73z_x1tH9DiMdeK8wgzmlnYKrwP6H3TUGjl_VH2wWAIp-aqVKG5NiCqWeUlhGsjM1fqrQBuTbejb7SokUMABLGEnkoGsDs57etgxG92BFxy9mJe3426Kk36MdSjBfDM6jWq4woUG-4i-1dYxgfYgVSSn2uuUuwUOWJJswsDsJtWgxfep4x2ZYZ8S0iRzheDQX7i7bKBBsROr_Qjp3nOHyyd17rLWpMfckq9jSY14Xji9F74K6sElFqypyzGFQvEq2--Iw")
                    .get().build();
            Response response = client.newCall(request).execute();
            log.info("====== {} {} {}", args, response.sentRequestAtMillis(), response.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }


    } @SneakyThrows
    public static void initMeetingPoster(String args) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()

                    .build();
            Request request = new Request.Builder()
                    .url("https://jbrainprod-gateway.myxjp.com/API-XJP/pharmbrain/meeting/initMeetingPoster?meetingNo=" + args + "&sourceName=ams")
                    .addHeader("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHTUx3MWUxSGo4VVZkY0gtNVJPeDdUdld1T01vT1N2cURESU5pQ3MxbG9FIn0.eyJleHAiOjE3MjE4OTU1NjIsImlhdCI6MTcxOTMwMzU2MiwianRpIjoiMmQ4YWZkZTktZGFkMy00OTI3LTlhMTMtMzBmYmMzYmFhNzE1IiwiaXNzIjoiaHR0cHM6Ly9qYnJhaW5wcm9kLm15eGpwLmNvbS9hdXRoL3JlYWxtcy9TVEVQIiwic3ViIjoiYjM4NzBjNDItOTQwNS00NjQ0LWEzNTItZWQ2ZDY5ODZiZDg5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHVibGljLWxvZ2luLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiI5OGU1ZjdjOC02NTMxLTQ0YWYtODM1Mi0yNzM2ZjdkMTQ0YTQiLCJzY29wZSI6InByb2ZpbGUiLCJzaWQiOiI5OGU1ZjdjOC02NTMxLTQ0YWYtODM1Mi0yNzM2ZjdkMTQ0YTQiLCJuYW1lIjoiamNyZHRlc3QiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiI3MDI0MDg2NTgiLCJnaXZlbl9uYW1lIjoiamNyZHRlc3QifQ.aLAeKYYAn96nK9_E3Ya43HOtYo1BsKElNQL5l7z-SA5qE6EfO__zqqbutX_7A4tgL-n0u5YZgK-Y_HNwM73z_x1tH9DiMdeK8wgzmlnYKrwP6H3TUGjl_VH2wWAIp-aqVKG5NiCqWeUlhGsjM1fqrQBuTbejb7SokUMABLGEnkoGsDs57etgxG92BFxy9mJe3426Kk36MdSjBfDM6jWq4woUG-4i-1dYxgfYgVSSn2uuUuwUOWJJswsDsJtWgxfep4x2ZYZ8S0iRzheDQX7i7bKBBsROr_Qjp3nOHyyd17rLWpMfckq9jSY14Xji9F74K6sElFqypyzGFQvEq2--Iw")
                    .get().build();
            Response response = client.newCall(request).execute();
            log.info("====== {} {} {}", args, response.sentRequestAtMillis(), response.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @SneakyThrows
    public static void initMeetingSignQR(String args) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()

                    .build();
            Request request = new Request.Builder()
                    .url("https://jbrainprod-gateway.myxjp.com/API-XJP/pharmbrain/meeting/initMeetingSignQR?meetingNo=" + args + "&sourceName=ams")
                    .addHeader("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHTUx3MWUxSGo4VVZkY0gtNVJPeDdUdld1T01vT1N2cURESU5pQ3MxbG9FIn0.eyJleHAiOjE3MjE4OTU1NjIsImlhdCI6MTcxOTMwMzU2MiwianRpIjoiMmQ4YWZkZTktZGFkMy00OTI3LTlhMTMtMzBmYmMzYmFhNzE1IiwiaXNzIjoiaHR0cHM6Ly9qYnJhaW5wcm9kLm15eGpwLmNvbS9hdXRoL3JlYWxtcy9TVEVQIiwic3ViIjoiYjM4NzBjNDItOTQwNS00NjQ0LWEzNTItZWQ2ZDY5ODZiZDg5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHVibGljLWxvZ2luLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiI5OGU1ZjdjOC02NTMxLTQ0YWYtODM1Mi0yNzM2ZjdkMTQ0YTQiLCJzY29wZSI6InByb2ZpbGUiLCJzaWQiOiI5OGU1ZjdjOC02NTMxLTQ0YWYtODM1Mi0yNzM2ZjdkMTQ0YTQiLCJuYW1lIjoiamNyZHRlc3QiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiI3MDI0MDg2NTgiLCJnaXZlbl9uYW1lIjoiamNyZHRlc3QifQ.aLAeKYYAn96nK9_E3Ya43HOtYo1BsKElNQL5l7z-SA5qE6EfO__zqqbutX_7A4tgL-n0u5YZgK-Y_HNwM73z_x1tH9DiMdeK8wgzmlnYKrwP6H3TUGjl_VH2wWAIp-aqVKG5NiCqWeUlhGsjM1fqrQBuTbejb7SokUMABLGEnkoGsDs57etgxG92BFxy9mJe3426Kk36MdSjBfDM6jWq4woUG-4i-1dYxgfYgVSSn2uuUuwUOWJJswsDsJtWgxfep4x2ZYZ8S0iRzheDQX7i7bKBBsROr_Qjp3nOHyyd17rLWpMfckq9jSY14Xji9F74K6sElFqypyzGFQvEq2--Iw")
                    .get().build();
            Response response = client.newCall(request).execute();
            log.info("====== {} {} {}", args, response.sentRequestAtMillis(), response.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @SneakyThrows
    public static void updateDoctorCoverage(String args) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()

                    .build();
            Request request = new Request.Builder()
                    .url("https://jbrainprod-gateway.myxjp.com/API-SALEA/doctorInformation/updateDoctorCoverage?" + args)
                    .addHeader("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHTUx3MWUxSGo4VVZkY0gtNVJPeDdUdld1T01vT1N2cURESU5pQ3MxbG9FIn0.eyJleHAiOjE3MTk1NjExMjcsImlhdCI6MTcxNjk2OTEyNywianRpIjoiNzdkZWI1MDMtMzNkYS00Zjk5LWI2MTUtMTFkMDQ3OTBiYmEyIiwiaXNzIjoiaHR0cHM6Ly9qYnJhaW5wcm9kLm15eGpwLmNvbS9hdXRoL3JlYWxtcy9TVEVQIiwic3ViIjoiZTZmY2QzOWMtYTEyYy00YjM4LWE2YjEtNjZlMjFiZmViNmMwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHVibGljLWxvZ2luLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiI4MzJkNDg2ZS1mNWI2LTQzZTctYmU5Yy0zODhhOTcyNGY5MWUiLCJzY29wZSI6InByb2ZpbGUiLCJzaWQiOiI4MzJkNDg2ZS1mNWI2LTQzZTctYmU5Yy0zODhhOTcyNGY5MWUiLCJuYW1lIjoi5ZGo55CoIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiNzAyNDI0NDAyIiwiZ2l2ZW5fbmFtZSI6IuWRqOeQqCJ9.I4EUXQAJhZYL__WPm9vhvG6hEJ061NBZyGR59DFAJ4vPBs6xIKY0Yh6t2rvrtyR3zKAF4PRsIAfT7esbAW-nUIb5YuecYyfkl9_fF5WjYPogPXbcqe2KmsmPBtDY6-lFsPNJyAnZbZ9LLWQZKrLQ2EVH8epaLPixKW4XbB5h-sJLRgPFfBH02YjzBP5ODrlb-dZGPTxDYHjqcTKUyiZVNI_4bx-j0-TvRcVYXhfxHFhwHQbW9eRAd9HRaqHSzgZy0cjKxLEScyR_3xMJ5_tVkioNjmkHfd9H49vggSytkWNhMlY7Kx_9jJPM75VrNFrWY4HeieyLf1dJpfGU730qXA")
                    .get().build();
            Response response = client.newCall(request).execute();
            log.info("====== {} {} {}", args, response.sentRequestAtMillis(), response.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @SneakyThrows
    public static void fileAddress(String args, JSONArray jsonArray) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 设置连接超时时间为30秒
                .readTimeout(30, TimeUnit.SECONDS) // 设置读取超时时间为30秒
                .build();
        Request request = new Request.Builder()
                .url(args)
                .addHeader("authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHTUx3MWUxSGo4VVZkY0gtNVJPeDdUdld1T01vT1N2cURESU5pQ3MxbG9FIn0.eyJleHAiOjE2OTcxODc4NTYsImlhdCI6MTY5NDU5NTg1NiwianRpIjoiNWY0MzgzZGEtY2ZjMC00YjhkLWEwNGEtZDExMTZiYTdmMDJmIiwiaXNzIjoiaHR0cHM6Ly9qYnJhaW5wcm9kLm15eGpwLmNvbS9hdXRoL3JlYWxtcy9TVEVQIiwic3ViIjoiYjM4NzBjNDItOTQwNS00NjQ0LWEzNTItZWQ2ZDY5ODZiZDg5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHVibGljLWxvZ2luLWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiI4YjcwNjRiYy00YTRhLTQ2NzktOTA4Zi0wYjdjZGU4OWRjNDMiLCJzY29wZSI6InByb2ZpbGUiLCJzaWQiOiI4YjcwNjRiYy00YTRhLTQ2NzktOTA4Zi0wYjdjZGU4OWRjNDMiLCJuYW1lIjoiamNyZHRlc3QiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiI3MDI0MDg2NTgiLCJnaXZlbl9uYW1lIjoiamNyZHRlc3QifQ.Kp7-g3kc_mmcJhJfX_RV96uMEtJSsLykVUJ8XddbLEGBCEta197MjZVIzwGyRGIZwCGxTh7A3Vuci84t-0hj4dXWBCJy2pQcV1R5zQGGxg2dW_uY6mx6YTrsk9Rzy--2bL8c2TjSRYSiYy8b4re01rTgR0sk_m6cnHUxmORvXIg-4xWdpNOWeUiFeeQ7zjvte43-oRI3iUocbOsit1-kvmxt853sqMJbi0DwseugxdkDnDouiQYA8Hbl_lob59HqbErTweZUaxImC_0Hx5NbFCUfELHZ4ZQP8OvPz-_G9vsR9JgdMKcK4XmLvrgF-Vg6DINeVeJWHGCjrSBwQNmEgg")
                .get().build();
        Response response = client.newCall(request).execute();
        if (!(response.body().string().length() > 100)) {
            jsonArray.add(args);
        }
        log.info("====== {} {} {}", args, response.sentRequestAtMillis(), response.isSuccessful());

    }

    @SneakyThrows
    public static void createCallVisit(String callId, String doctorId, String authorization) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://jbrainprod-gateway.myxjp.com/API-SALEA/visitRecord/v3/createCallVisit?doctorId=" + doctorId + "&callId=" + callId + "&recordType=代表呼出&taskId")
                .method("POST", body)
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:123.0) Gecko/20100101 Firefox/123.0")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("loginSys", "idatapharm")
                .addHeader("authorization", authorization)
                .addHeader("system", "pc")
                .addHeader("language", "zh")
                .addHeader("Origin", "https://jbrainprod.myxjp.com")
                .addHeader("DNT", "1")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "https://jbrainprod.myxjp.com/")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Site", "same-site")
                .build();
        Response response = client.newCall(request).execute();
        log.info("====== {}", response.body());

    }

    @SneakyThrows
    public static void visitRecordDelete(String id, String authorization) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://jbrainprod-gateway.myxjp.com/API-SALEA/visitRecord/v3/delete?code=admin007&id=" + id)
                .method("DELETE", body)
                .addHeader("Authorization", authorization)
                .build();
        Response response = client.newCall(request).execute();
        log.info("====== {}", response.body());

    }

    /**
     * Get string.
     *
     * @param url     the url
     * @param headers the headers
     * @return the string
     * @throws IOException the io exception
     */
    public static String get(String url, Map<String, String> headers) throws IOException {
        return newCall(HttpMethod.GET, url, headers, null);
    }

    /**
     * Get string.
     *
     * @param url the url
     * @return the string
     * @throws IOException the io exception
     */
    public static String get(String url) throws IOException {
        return newCall(HttpMethod.GET, url, Maps.newHashMap(), null);
    }

    /**
     * Head string.
     *
     * @param url     the url
     * @param headers the headers
     * @return the string
     * @throws IOException the io exception
     */
    public static String head(String url, Map<String, String> headers) throws IOException {
        return newCall(HttpMethod.HEAD, url, headers, null);
    }


    /**
     * Head string.
     *
     * @param url the url
     * @return the string
     * @throws IOException the io exception
     */
    public static String put(String url) throws IOException {
        return newCall(PUT, url, Maps.newHashMap(), null);
    }


    /**
     * Post string.
     *
     * @param url         the url
     * @param requestBody the request body
     * @return the string
     * @throws IOException the io exception
     */
    public static String post(String url, RequestBody requestBody) throws IOException {
        return newCall(POST, url, Maps.newHashMap(), requestBody);
    }

    /**
     * Post string.
     *
     * @param url         the url
     * @param headers     the headers
     * @param requestBody the request body
     * @return the string
     * @throws IOException the io exception
     */
    public static String post(String url, Map<String, String> headers, RequestBody requestBody) throws IOException {
        return newCall(POST, url, headers, requestBody);
    }

    /**
     * Put string.
     *
     * @param url         the url
     * @param requestBody the request body
     * @return the string
     * @throws IOException the io exception
     */
    public static String put(String url, RequestBody requestBody) throws IOException {
        return newCall(PUT, url, Maps.newHashMap(), requestBody);
    }

    /**
     * Put string.
     *
     * @param url         the url
     * @param headers     the headers
     * @param requestBody the request body
     * @return the string
     * @throws IOException the io exception
     */
    public static String put(String url, Map<String, String> headers, RequestBody requestBody) throws IOException {
        return newCall(PUT, url, headers, requestBody);
    }

    /**
     * Patch string.
     *
     * @param url         the url
     * @param requestBody the request body
     * @return the string
     * @throws IOException the io exception
     */
    public static String patch(String url, RequestBody requestBody) throws IOException {
        return newCall(PATCH, url, Maps.newHashMap(), requestBody);
    }

    /**
     * Patch string.
     *
     * @param url         the url
     * @param headers     the headers
     * @param requestBody the request body
     * @return the string
     * @throws IOException the io exception
     */
    public static String patch(String url, Map<String, String> headers, RequestBody requestBody) throws IOException {
        return newCall(PATCH, url, headers, requestBody);
    }

    /**
     * Delete string.
     *
     * @param url         the url
     * @param requestBody the request body
     * @return the string
     * @throws IOException the io exception
     */
    public static String delete(String url, RequestBody requestBody) throws IOException {
        return newCall(HttpMethod.DELETE, url, Maps.newHashMap(), requestBody);
    }

    /**
     * Delete string.
     *
     * @param url         the url
     * @param headers     the headers
     * @param requestBody the request body
     * @return the string
     * @throws IOException the io exception
     */
    public static String delete(String url, Map<String, String> headers, RequestBody requestBody) throws IOException {
        return newCall(HttpMethod.DELETE, url, headers, requestBody);
    }


    private static String newCall(HttpMethod method, String url, Map<String, String> headers, RequestBody requestBody) throws IOException {
        Request.Builder builder = new Request.Builder().url(url);
        if (!headers.isEmpty()) {
            builder.headers(Headers.of(headers));
        }
        if (method.equals(POST)) {
            builder.post(requestBody);
        } else if (method.equals(PUT)) {
            builder.put(requestBody);
        } else if (method.equals(PATCH)) {
            builder.patch(requestBody);
        } else if (method.equals(DELETE)) {
            builder.delete(requestBody);
        } else if (method.equals(HEAD)) {
            builder.head();
        } else {
            builder.get();
        }
        try (Response response = client.newCall(builder.build()).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // ... do something with response
                return responseBody;
            } else {
                throw new RuntimeException("" + response);
            }
        } catch (IOException e) {
            // ... handle IO exception
            throw e;
        }
    }


}
