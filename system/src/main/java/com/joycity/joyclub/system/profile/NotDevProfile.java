package com.joycity.joyclub.system.profile;

/**
 * Created by CallMeXYZ on 2017/7/10.
 */

import com.joycity.joyclub.commons.constant.Global;
import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Profile(Global.PROFILE_NOT_DEVELOPMENT)
public @interface NotDevProfile {
}
