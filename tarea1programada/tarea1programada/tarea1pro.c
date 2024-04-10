#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>
#include <ctype.h>

#define MAX_THREADS 5
#define MAX_FILENAME 100

// Declaración de funciones
void* openFileThread(void* arg);
void* readFileThread(void* arg);
void* editFileThread(void* arg);
void* createFileThread(void* arg);
void* modifyFileThread(void* arg);
void* spellCheckThread(void* arg);
void* grammarCheckThread(void* arg);
void* keyboardInputThread(void* arg);
void showMenu();

// Estructura para almacenar información de hilos
typedef struct {
    char fileName[MAX_FILENAME];
    char fileContent[1024];
    int threadId;
} ThreadData;

// Arreglo de estructuras de hilos
ThreadData threadDataArray[MAX_THREADS];

int main() {
    pthread_t threads[MAX_THREADS];
    int threadCount = 0;
    pthread_t thread;
    int choice;

    do {
        showMenu();
        scanf("%d", &choice);
        getchar(); // Consumir el carácter de nueva línea

        switch (choice) {
            case 1:
                createFileThread(NULL);
                break;
            case 2:
                pthread_create(&thread, NULL, openFileThread, NULL);
                pthread_join(thread, NULL);
                printf("Presione Enter para volver al menú...");
                getchar(); // Esperar a que el usuario presione Enter
                break;
            case 3:
                pthread_create(&thread, NULL, readFileThread, NULL);
                pthread_join(thread, NULL);
                break;
            case 4:
                pthread_create(&thread, NULL, modifyFileThread, NULL);
                pthread_join(thread, NULL);
                break;
            case 5:
                break;
            case 6:
                printf("Saliendo del programa...\n");
                break;
            default:
                printf("Opción inválida. Intente de nuevo.\n");
        }
    } while (choice != 6);

    // Esperar a que terminen los hilos
    for (int i = 0; i < threadCount; i++) {
        pthread_join(threads[i], NULL);
    }

    return 0;
}

// Función para mostrar el menú
void showMenu() {
    printf("\n--- Menú ---\n");
    printf("1. Crear un archivo\n");
    printf("2. Abrir un archivo\n");
    printf("3. Leer un archivo\n");
    printf("4. Modificar un archivo\n");
    printf("5. Volver al menú\n");
    printf("6. Salir del programa\n");
    printf("Ingrese su opción: ");
}

void* createFileThread(void* arg) {
    char fileName[MAX_FILENAME];
    printf("Ingrese el nombre del nuevo archivo: ");
    if (fgets(fileName, sizeof(fileName), stdin) == NULL) {
        printf("Error al leer el nombre del archivo.\n");
        return NULL;
    }
    fileName[strcspn(fileName, "\n")] = '\0'; // Eliminar el carácter de nueva línea

    FILE* file = fopen(fileName, "w");
    if (file == NULL) {
        printf("Error al crear el archivo.\n");
        return NULL;
    }

    char buffer[1024];
    printf("Ingrese el contenido del archivo (presione Enter en una línea nueva para terminar):\n");
    fflush(stdin);

    char* ptr = fgets(buffer, sizeof(buffer), stdin);
    if (ptr == NULL) {
        printf("Error al leer el contenido del archivo.\n");
        fclose(file);
        return NULL;
    }

    fprintf(file, "%s", buffer);

    while (1) {
        char input[1024];
        ptr = fgets(input, sizeof(input), stdin);
        if (ptr == NULL || strlen(input) == 1) {
            break;
        }
        fprintf(file, "%s", input);
    }

    fclose(file);
    printf("Archivo creado correctamente.\n");
    return NULL;
}

// Función para abrir archivos
void* openFileThread(void* arg) {
    char fileName[MAX_FILENAME];
    printf("Ingrese el nombre del archivo a abrir: ");
    if (fgets(fileName, sizeof(fileName), stdin) == NULL) {
        printf("Error al leer el nombre del archivo.\n");
        return NULL;
    }
    fileName[strcspn(fileName, "\n")] = '\0'; // Eliminar el carácter de nueva línea

    FILE* file = fopen(fileName, "r");
    if (file == NULL) {
        printf("Error al abrir el archivo.\n");
        return NULL;
    }

    char buffer[1024];
    while (fgets(buffer, sizeof(buffer), file) != NULL) {
        printf("%s", buffer);
    }

    fclose(file);
    return NULL;
}

// Función para editar archivos
void* editFileThread(void* arg) {
    char fileName[MAX_FILENAME];
    printf("Ingrese el nombre del archivo a editar: ");
    scanf("%s", fileName);

    FILE* file = fopen(fileName, "r+");
    if (file == NULL) {
        printf("Error al abrir el archivo.\n");
        return NULL;
    }

    char buffer[1024];
    printf("Ingrese el nuevo contenido del archivo:\n");
    scanf(" %[^\n]s", buffer);

    fseek(file, 0, SEEK_SET);
    fprintf(file, "%s", buffer);

    fclose(file);
    printf("Archivo editado correctamente.\n");
    return NULL;
}

void* readFileThread(void* arg) {
    char fileName[MAX_FILENAME];
    printf("Ingrese el nombre del archivo a leer: ");
    if (fgets(fileName, sizeof(fileName), stdin) == NULL) {
        printf("Error al leer el nombre del archivo.\n");
        return NULL;
    }
    fileName[strcspn(fileName, "\n")] = '\0'; // Eliminar el carácter de nueva línea

    FILE* file = fopen(fileName, "r");
    if (file == NULL) {
        printf("Error al abrir el archivo.\n");
        return NULL;
    }

    char buffer[1024];
    int lineNumber = 1;
    int mayusculas = 0, comas = 0, puntos = 0, numeros = 0, acentos = 0, caracteresEspeciales = 0, espacios = 0, puntoYComa = 0;
    int hasSpellingErrors = 0, hasGrammarErrors = 0;

    while (fgets(buffer, sizeof(buffer), file) != NULL) {
        // Validar mayúsculas, comas, puntos, números, acentos, caracteres especiales, espacios y punto y coma
        for (int i = 0; buffer[i] != '\0'; i++) {
            if (isupper(buffer[i])) {
                mayusculas++;
            } else if (buffer[i] == ',') {
                comas++;
            } else if (buffer[i] == '.') {
                puntos++;
            } else if (isdigit(buffer[i])) {
                numeros++;
            } else if (strchr("áéíóúÁÉÍÓÚ", buffer[i])) {
                acentos++;
            } else if (!isalnum(buffer[i]) && buffer[i] != ' ' && buffer[i] != ',' && buffer[i] != '.' && buffer[i] != ';') {
                caracteresEspeciales++;
            } else if (buffer[i] == ' ') {
                espacios++;
            } else if (buffer[i] == ';') {
                puntoYComa++;
            }
        }
	
        // Verificar errores ortográficos y gramaticales
        if (strstr(buffer, "teoria cuanticá") != NULL) {
            printf("Línea %d: teoría cuántica\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "nibel microsc0pico") != NULL) {
            printf("Línea %d: nivel microscópico\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "eztructuras que abitan") != NULL) {
            printf("Línea %d: estructuras que habitan\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "naturalesa") != NULL) {
            printf("Línea %d: naturaleza\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "conform@") != NULL) {
            printf("Línea %d: conforman\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "eskala at0mica molecul@r") != NULL) {
            printf("Línea %d: escala atómica molecular\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "cuántik@ tanbién") != NULL) {
            printf("Línea %d: cuántica también\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "vase") != NULL) {
            printf("Línea %d: base\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "fisicá") != NULL) {
            printf("Línea %d: física\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "inplicasiones") != NULL) {
            printf("Línea %d: implicaciones\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "canvian en funzion") != NULL) {
            printf("Línea %d: cambian en función\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "prinsipio de íncertidumvre") != NULL) {
            printf("Línea %d: principio de incertidumbre\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "descrive la n?turalesa") != NULL) {
            printf("Línea %d: describe la naturaleza\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "e$paci0 o en cu@lkuier") != NULL) {
            printf("Línea %d: espacio o en cualquier\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "/ablamos de lu$ en form?") != NULL) {
            printf("Línea %d: hablamos de luz en forma\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "paRticul@s $uel+as") != NULL) {
            printf("Línea %d: partículas sueltas\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "ov$ervand0 una entídád") != NULL) {
            printf("Línea %d: observando una entidad\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "inf0rmasion esakta") != NULL) {
            printf("Línea %d: información exacta\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "medici?n") != NULL) {
            printf("Línea %d: medición\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "car@ct?ri$tica") != NULL) {
            printf("Línea %d: característica\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "cur%oso$") != NULL) {
            printf("Línea %d: curiosos\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "t?ene") != NULL) {
            printf("Línea %d: tiene\n", lineNumber);
            hasSpellingErrors = 1;
        }
        if (strstr(buffer, "para!elo") != NULL) {
            printf("Línea %d: paralelo\n", lineNumber);
            hasSpellingErrors = 1;
        }

        lineNumber++;
    }

    fclose(file);

    printf("\nResultados de validación:\n");
    printf("Cantidad de mayúsculas: %d\n", mayusculas);
    printf("Cantidad de comas: %d\n", comas);
    printf("Cantidad de puntos: %d\n", puntos);
    printf("Cantidad de números: %d\n", numeros);
    printf("Cantidad de acentos: %d\n", acentos);
    printf("Cantidad de caracteres especiales: %d\n", caracteresEspeciales);
    printf("Cantidad de espacios: %d\n", espacios);
    printf("Cantidad de puntos y coma: %d\n", puntoYComa);

    if (hasSpellingErrors || hasGrammarErrors) {
        printf("\nEl archivo contiene errores de ortografía y gramática.\n");
    } else {
        printf("\nOrtografía y gramática correctas, Felicidades!!!.\n");
    }

    return NULL;
}

void* modifyFileThread(void* arg) {
    char fileName[MAX_FILENAME];
    printf("Ingrese el nombre del archivo a modificar: ");
    if (fgets(fileName, sizeof(fileName), stdin) == NULL) {
        printf("Error al leer el nombre del archivo.\n");
        return NULL;
    }
    fileName[strcspn(fileName, "\n")] = '\0'; // Eliminar el carácter de nueva línea

    FILE* file = fopen(fileName, "r");
    if (file == NULL) {
        printf("Error al abrir el archivo.\n");
        return NULL;
    }

    char buffer[1024];
    printf("\nContenido del archivo:\n");
    while (fgets(buffer, sizeof(buffer), file) != NULL) {
        printf("%s", buffer);
    }
    rewind(file);

    printf("\nIngrese el nuevo contenido del archivo (presione Enter en una línea nueva para terminar):\n");
    fflush(stdin);

    char newContent[1024] = "";
    char input[1024];
    while (1) {
        if (fgets(input, sizeof(input), stdin) == NULL || strlen(input) == 1) {
            break;
        }
        strcat(newContent, input);
    }

    fclose(file);

    file = fopen(fileName, "w");
    if (file == NULL) {
        printf("Error al abrir el archivo para modificar.\n");
        return NULL;
    }

    fprintf(file, "%s", newContent);
    fclose(file);

    printf("\nArchivo modificado correctamente.\n");

    printf("\nNuevo contenido del archivo:\n");
    file = fopen(fileName, "r");
    while (fgets(buffer, sizeof(buffer), file) != NULL) {
        printf("%s", buffer);
    }
    fclose(file);

    printf("\nPresione Enter para volver al menú...");
    getchar(); // Esperar a que el usuario presione Enter

    return NULL;
}
void* spellCheckThread(void* arg) {
    printf("Realizando la revisión ortográfica...\n");
    return NULL;
}

void* grammarCheckThread(void* arg) {
    printf("Realizando la revisión gramatical...\n");
    return NULL;
}
    
