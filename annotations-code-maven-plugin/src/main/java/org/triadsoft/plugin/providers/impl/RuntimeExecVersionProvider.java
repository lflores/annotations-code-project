package org.triadsoft.plugin.providers.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.plugin.MojoExecutionException;
import org.triadsoft.plugin.providers.VersionProvider;

@Named
@Singleton
public class RuntimeExecVersionProvider implements VersionProvider {
    @Override
    public String getVersion(String command) throws MojoExecutionException {
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            // Verificar si el proceso terminó correctamente (exitCode == 0)
            if (exitCode == 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                return reader.readLine();
            } else {
                throw new MojoExecutionException(
             "Execution of command '" + command + "' failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new MojoExecutionException("Execution of command '" + command + "' failed", e);
        }
    }
}
