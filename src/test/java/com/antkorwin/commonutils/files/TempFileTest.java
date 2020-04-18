package com.antkorwin.commonutils.files;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

import com.antkorwin.throwable.functions.WrappedException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

class TempFileTest {

    private final static String TEST_DATA = "test data";

    @Nested
    class EvaluateTests {

        @Test
        void readDataFromStream() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            String result = new TempFile(() -> inputStream)
                    .evaluate(f -> FileUtils.readFileToString(f, UTF_8));
            // Assert
            assertThat(result).isEqualTo(TEST_DATA);
        }

        @Test
        void readDataFromStreamMultipleTimes() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            String result = new TempFile(() -> inputStream)
                    .evaluate(f -> {
                        // read first time
                        String data = FileUtils.readFileToString(f, UTF_8);
                        assertThat(data).isEqualTo(TEST_DATA);
                        // read second time
                        return FileUtils.readFileToString(f, UTF_8);
                    });
            // Assert
            assertThat(result).isEqualTo(TEST_DATA);
        }

        @Test
        void createTempFile() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            Long size = new TempFile(() -> inputStream)
                    .evaluate(f -> {
                        assertThat(new File(f.getAbsolutePath()).exists()).isTrue();
                        return f.length();
                    });
            // Assert
            assertThat(size).isEqualTo(TEST_DATA.getBytes().length);
        }

        @Test
        void deleteFileAfterProcessing() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            String fileName = new TempFile(() -> inputStream).evaluate(File::getAbsolutePath);
            // Assert
            assertThat(new File(fileName).exists()).isFalse();
        }
    }

    @Nested
    class EvaluateThrowsExceptionsTests {

        @Test
        void throwUncheckedExceptionInProcessingFunc() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            RuntimeException exc = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
                new TempFile(() -> inputStream)
                        .evaluate(f -> {
                            // Act
                            throw new IndexOutOfBoundsException("oops");
                        });
            });
            // Assert
            assertThat(exc.getMessage()).isEqualTo("oops");
        }

        @Test
        void throwCheckedExceptionInProcessingFunc() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            WrappedException exc = Assertions.assertThrows(WrappedException.class, () -> {
                new TempFile(() -> inputStream)
                        .evaluate(f -> {
                            // Act
                            throw new IOException("oops");
                        });
            });
            // Assert
            assertThat(exc.getMessage()).contains("oops");
            assertThat(exc.getCause()).isInstanceOf(IOException.class);
        }

        @Test
        void throwUncheckedExceptionFromInputStreamSupplier() {

            // Act
            IndexOutOfBoundsException exc = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
                new TempFile(() -> {
                    throw new IndexOutOfBoundsException("oops");
                }).evaluate(f -> "123");
            });
            // Assert
            assertThat(exc.getMessage()).contains("oops");
        }

        @Test
        void throwCheckedExceptionFromInputStreamSupplier() {

            // Act
            WrappedException exc = Assertions.assertThrows(WrappedException.class, () -> {
                new TempFile(() -> {
                    throw new IOException("oops");
                }).evaluate(f -> "123");
            });
            // Assert
            assertThat(exc.getMessage()).contains("oops");
            assertThat(exc.getCause()).isInstanceOf(IOException.class);
        }
    }

    @Nested
    class RunTests {

        @Test
        void readDataFromStream() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            new TempFile(() -> inputStream)
                    .run(f -> {
                        String data = FileUtils.readFileToString(f, UTF_8);
                        assertThat(data).isEqualTo(TEST_DATA);
                    });
        }

        @Test
        void createTempFile() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            new TempFile(() -> inputStream)
                    .run(f -> {
                        assertThat(new File(f.getAbsolutePath()).exists()).isTrue();
                        assertThat(f.length()).isEqualTo(TEST_DATA.getBytes().length);
                    });
        }

        @Test
        void deleteFileAfterProcessing() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            AtomicReference<String> fileName = new AtomicReference<>();
            // Act
            new TempFile(() -> inputStream).run(f -> {
                fileName.set(f.getAbsolutePath());
            });
            // Assert
            assertThat(new File(fileName.get()).exists()).isFalse();
        }
    }

    @Nested
    class RunThrowsExceptionsTests {

        @Test
        void throwUncheckedExceptionInProcessingFunc() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            RuntimeException exc = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
                new TempFile(() -> inputStream)
                        .run(f -> {
                            // Act
                            throw new IndexOutOfBoundsException("oops");
                        });
            });
            // Assert
            assertThat(exc.getMessage()).isEqualTo("oops");
        }

        @Test
        void throwCheckedExceptionInProcessingFunc() {
            // Arrange
            InputStream inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
            // Act
            WrappedException exc = Assertions.assertThrows(WrappedException.class, () -> {
                new TempFile(() -> inputStream)
                        .run(f -> {
                            // Act
                            throw new IOException("oops");
                        });
            });
            // Assert
            assertThat(exc.getMessage()).contains("oops");
            assertThat(exc.getCause()).isInstanceOf(IOException.class);
        }

        @Test
        void throwUncheckedExceptionFromInputStreamSupplier() {

            // Act
            IndexOutOfBoundsException exc = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
                new TempFile(() -> {
                    throw new IndexOutOfBoundsException("oops");
                }).run(f -> {/* nop */});
            });
            // Assert
            assertThat(exc.getMessage()).contains("oops");
        }

        @Test
        void throwCheckedExceptionFromInputStreamSupplier() {

            // Act
            WrappedException exc = Assertions.assertThrows(WrappedException.class, () -> {
                new TempFile(() -> {
                    throw new IOException("oops");
                }).run(f -> {/* nop */});
            });
            // Assert
            assertThat(exc.getMessage()).contains("oops");
            assertThat(exc.getCause()).isInstanceOf(IOException.class);
        }
    }
}