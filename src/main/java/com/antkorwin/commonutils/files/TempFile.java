package com.antkorwin.commonutils.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;

import com.antkorwin.throwable.functions.ThrowableConsumer;
import com.antkorwin.throwable.functions.ThrowableFunction;
import com.antkorwin.throwable.functions.ThrowableSupplier;
import com.antkorwin.throwable.functions.WrappedException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;

/**
 * Created on 16/04/2020
 * <p>
 * Temporary file processing tool.
 *
 * @author Korovin Anatoliy
 */
@RequiredArgsConstructor
public class TempFile {

    /**
     * Supplier of the InputStream with input data
     */
    private final ThrowableSupplier<InputStream> inputStreamSupplier;

    /**
     * This method saves data from the input stream into the temporary file
     * then you can make something with this file independently of the input stream.
     * <p>
     * For example, you can read this file multiple times in the body of processing function.
     * <p>
     * After the exit of the processing function, the temporary file will be deleted.
     * <br><br>
     * Same as the {@link #evaluate(ThrowableFunction)} but without the necessary to return a value.
     *
     * @param processing the processing function for the file which built from the input stream
     */
    public void run(ThrowableConsumer<File> processing) {
        evaluate(f -> {
            processing.accept(f);
            return null;
        });
    }

    /**
     * This method saves data from the input stream into the temporary file
     * then you can make something with this file independently of the input stream.
     * <p>
     * For example, you can read this file multiple times in the body of processing function.
     * <p>
     * After the exit of the processing function, the temporary file will be deleted.
     *
     * @param processingFunction   the processing function for the file which built from the input stream
     * @param <ConsumerReturnType> the type of processing function result
     * @return the result of processing function
     */
    public <ConsumerReturnType> ConsumerReturnType evaluate(ThrowableFunction<File, ConsumerReturnType> processingFunction) {
        File tempFile = null;
        try {
            tempFile = createTempFile();
            copyDataFromStreamToFile(tempFile);
            return processingFunction.apply(tempFile);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    /**
     * Create a temporary file with a random name
     *
     * @return return create file
     */
    private File createTempFile() {
        try {
            return Files.createTempFile(UUID.randomUUID().toString(), "tmp").toFile();
        } catch (IOException e) {
            throw new WrappedException(e);
        }
    }

    /**
     * Copy an original data form the InputStream into the temp file.
     *
     * @param tempFile in this file will be copy data from {@link #inputStreamSupplier}
     * @throws IOException exception which may happened while you read a data from stream
     */
    private void copyDataFromStreamToFile(File tempFile) {
        try {
            try (InputStream inputStream = inputStreamSupplier.get();
                 OutputStream outputStream = new FileOutputStream(tempFile)) {

                IOUtils.copy(inputStream, outputStream);
            }
        } catch (IOException e) {
            throw new WrappedException(e);
        }
    }
}
