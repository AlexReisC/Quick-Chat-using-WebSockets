# Chat em tempo real em SPring Boot + WebSockets

Este projeto é um sistema de chat em tempo real desenvolvido com Spring Boot e WebSockets.
Ele é composto por dois serviços principais:

1. `chat-service`: Responsável por gerenciar as mensagens e a comunicação em tempo real
   entre os usuários. Utiliza WebSockets para garantir a troca de mensagens instantânea.

2. `user-service`: Responsável pelo gerenciamento de usuários, incluindo autenticação,
   registro e manutenção de informações do perfil.

Funcionalidades principais:
- Comunicação em tempo real entre múltiplos usuários.
- Integração entre os serviços para autenticação e troca de mensagens.
- Arquitetura modular para facilitar a escalabilidade e manutenção.

Tecnologias utilizadas:
- Spring Boot para a construção dos serviços backend.
- WebSockets para comunicação em tempo real.
