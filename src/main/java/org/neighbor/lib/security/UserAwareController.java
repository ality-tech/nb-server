package org.neighbor.lib.security;

import org.neighbor.api.user.UserDto;
import org.springframework.security.core.Authentication;

/**
 *
 * Created by Konstantin Konyshev on 15/02/2017.
 */
public abstract class UserAwareController {

    /**
     * Returns UserDto for authorized requests or null for anonymous.
     *
     * @param authentication
     * @return
     */
    protected UserDto getUser(Authentication authentication) {
        return null;
    }
}
