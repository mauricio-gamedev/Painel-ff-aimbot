# FF Companion

Painel auxiliar Android para Free Fire, desenvolvido em Kotlin e Jetpack Compose.

## Recursos
- Perfis de sensibilidade com controles ajustáveis.
- Presets Equilibrado, Precisão e Rápido.
- Salvamento local usando SharedPreferences.
- Atalho para abrir Free Fire / Free Fire MAX quando instalado.
- Atalho para configurações de tela do Android.
- Interface escura com destaque vermelho.

Os valores de sensibilidade são referências para ajuste manual. O aplicativo não altera configurações internas do jogo.

## Requisitos
- Android Studio com JDK 17.
- Android SDK Platform 36.
- Android 8.0 (API 26) ou superior.

## Build
Abra o projeto no Android Studio e execute a configuração `:app`. O projeto usa Android Gradle Plugin 8.10.1 e Kotlin 2.1.21.

## Privacidade
Os perfis são armazenados localmente. O aplicativo não solicita permissões de rede, armazenamento, overlay, acessibilidade ou root.
