package org.triadsoft.plugin.providers;

import org.apache.maven.plugin.MojoExecutionException;

public interface VersionProvider {
    String getVersion(String command) throws MojoExecutionException;
}
