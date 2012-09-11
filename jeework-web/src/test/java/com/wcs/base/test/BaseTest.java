package com.wcs.base.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.runner.RunWith;

/**
 * 
 * @author Chris Guan
 */
@RunWith(Arquillian.class)
public abstract class BaseTest{
	@Deployment
	public static Archive<?> createDeployment() {
		final MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
				.loadMetadataFromPom("pom.xml").goOffline();
		return ShrinkWrap.create(WebArchive.class, "btcbase.war")
			.addAsResource("META-INF/persistence.xml")
			.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	
			.addPackages(true, "com/wcs")

			.addAsLibraries(resolver.configureFrom("D:\\dev\\build\\maven\\conf\\settings.xml")
                .artifact("org.primefaces:primefaces:3.0.1")
                .artifact("rapid:xsqlbuilder:1.0.4")
                .artifact("org.apache.commons:commons-lang3:3.1")
				.artifact("commons-collections:commons-collections:3.2.1")
				.artifact("commons-codec:commons-codec:1.5")
				.artifact("commons-io:commons-io:1.4")
				.artifact("commons-beanutils:commons-beanutils:1.8.0")
				.artifact("commons-configuration:commons-configuration:1.7")
				.artifact("com.google.guava:guava:r09")
				.artifact("ch.qos.cal10n:cal10n-api:0.7.4")
				.artifact("commons-fileupload:commons-fileupload:1.2.2")
				.artifact("org.apache.poi:poi:3.6")
				.artifact("org.apache.shiro:shiro-core:1.2.0")
				//.artifact("org.apache.shiro:shiro-faces:2.0-SNAPSHOT")
				.artifact("org.slf4j:slf4j-api:1.6.1")
				.artifact("org.slf4j:slf4j-ext:1.6.1")
				.artifact("org.slf4j:slf4j-log4j12:1.6.1")
				.artifact("log4j:log4j:1.2.16")
				//.artifact("net.sf.jasperreports:jasperreports")
                .resolveAsFiles());
	}
	
}
