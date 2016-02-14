package info.novatec.travelagency.utils;

import info.novatec.travelagency.services.Role;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import java.util.*;

import static java.lang.String.format;

@Interceptor
@Secured
public class SecuredInterceptor {

    @Resource
    SessionContext sessionContext;

    @AroundInvoke
    public Object checkCallerRoles(InvocationContext invocationContext) throws Exception {
        Set<Role> allowedRoles = getAllowedRoles(invocationContext);

        if (hasCallerRequiredRole(allowedRoles)) {
            return invocationContext.proceed();
        }

        throw new SecurityException(format("The caller role(s) %s do not match the required role(s) : %s",
                getCallerRoles(),
                allowedRoles));
    }

    private Set<Role> getCallerRoles() {
        Set<Role> result = new LinkedHashSet<>();

        for (Role role : Role.values()) {
            if (sessionContext.isCallerInRole(role.name())) {
                result.add(role);
            }
        }

        return result;
    }

    private boolean hasCallerRequiredRole(Set<Role> allowedRoles) {
        Set<Role> callerRoles = getCallerRoles();
        callerRoles.retainAll(allowedRoles);
        return !callerRoles.isEmpty();
    }

    private Set<Role> getAllowedRoles(InvocationContext context) {
        Secured secured = context.getMethod().getDeclaredAnnotation(Secured.class);
        return new LinkedHashSet<>(Arrays.asList(secured.value()));
    }
}
