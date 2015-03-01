package by.htp.krozov.android.sample.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Упрощенная модель Repository объекта Github.
 *
 * Настройка игнорирования полей происходит в
 * {@link by.htp.krozov.android.sample.githubclient.networking.GitHubServiceHolder}
 *
 * @author Kirill Rozov
 * @since 26.02.15.
 */
public class Repo {

    @JsonProperty("name")
    private String mName;

    @JsonProperty("full_name")
    private String mFullName;

    @JsonProperty("description")
    private String mDescription;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
