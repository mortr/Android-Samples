package by.htp.krozov.android.sample.githubclient.networking;

import java.util.List;

import by.htp.krozov.android.sample.githubclient.model.Repo;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Kirill Rozov
 * @since 25.02.15.
 */
public interface GitHubService {

    @GET("/users/{user}/repos")
    List<Repo> listRepos(@Path("user") String user);
}
