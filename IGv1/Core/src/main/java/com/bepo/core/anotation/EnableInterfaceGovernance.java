package com.bepo.core.anotation;

import com.bepo.core.action.IgController;
import com.bepo.core.registar.InterfaceGovernanceRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({InterfaceGovernanceRegistrar.class, IgController.class})
public @interface EnableInterfaceGovernance {

}
