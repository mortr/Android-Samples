package by.htp.krozov.android.sample.githubclient.networking;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

/**
 * @author Kirill Rozov
 * @since 26.02.15.
 */
public class GitHubServiceHolder {

    private static final class InstanceHolder {
        static final GitHubService INSTANCE;

        private static final String GITHUB_ENDPOINT = "https://api.github.com";

        static {
            RestAdapter.Builder builder = new RestAdapter.Builder();
            builder.setEndpoint(GITHUB_ENDPOINT);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

            // Изменяем конвертер данных в объекты
            builder.setConverter(new JacksonConverter(mapper));
            INSTANCE = builder.build().create(GitHubService.class);
        }

    }

    public static GitHubService getService() {
        return InstanceHolder.INSTANCE;
    }

    private GitHubServiceHolder() {
    }
}
