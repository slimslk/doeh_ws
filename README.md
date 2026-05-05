# DoEH: Domains of Endless Hunger
## WebSocket Gateway Service (Java/Netty)

A high-performance, asynchronous gateway responsible for real-time bidirectional communication between clients and the game ecosystem.

### Overview
This service acts as the primary entry point for all persistent client connections. Built with **Netty**, it ensures low-latency data transmission and manages the lifecycle of thousands of concurrent WebSocket connections using a non-blocking I/O model.

### Key Technical Features
*   **High-Concurrency Networking**: Implements **Netty (NIO)** for superior performance and efficient resource management under heavy load.
*   **Reactive Messaging Bridge**: Translates incoming WebSocket client actions into **Apache Kafka** events and broadcasts server-side updates back to the users.
*   **Production-Ready Security**:
    *   **JWT Authentication**: Secure handshake process using JSON Web Tokens.
    *   **SSL/TLS Support**: Configurable security protocols for encrypted communication with Kafka brokers.
*   **Scalability**: Stateless architecture designed for containerized environments (Docker).

### Tech Stack
*   **Language:** Java 21
*   **Network Engine:** Netty
*   **Messaging:** Apache Kafka
*   **Security:** JWT, SSL/TLS
*   **Containerization:** Docker

---

## Configuration & Deployment

The service is highly flexible and configured via environment variables. It is crucial that the Kafka topics match the configuration of the **Game Server** to ensure proper data flow.

### Configurable Message Broker Topics
The service dynamically routes data through topics defined in the configuration:
* **Upstream (Client → Server):**
    * `player.event.topic`: Receives raw player actions and pushes them to the game logic engine.
* **Downstream (Server → Client):**
    * `player.updates.topic`, `location.updates.topic`, `game.updates.topic`: Consumes processed game state updates to broadcast them to active WebSocket channels.

### Environment Variables
| Category | Variable | Description |
| :--- | :--- | :--- |
| **Main** | `ws.port` | Port for the WebSocket server |
| **Kafka** | `bootstrap.servers` | Kafka broker addresses |
| **Kafka** | `security.protocol` | Security protocol (e.g., SSL, SASL_SSL) |
| **JWT** | `jwt.token` | Signing key for HMAC-SHA256/SHA512 token validation |

---

## Getting Started

### Building with Docker
The service includes a `Dockerfile` for easy containerization:

```bash
# Build the image
docker build -t websocket-gateway .

# Run the container
docker run -p 8080:8080 -e ws.port=8080 websocket-gateway
```

---

## ⚖️ Legal & Licensing

**Copyright © 2025-2026 Dmytro Kuzavkov (slimslk). All rights reserved.**

This software and its associated files are **proprietary** and confidential. 
Unauthorized copying, distribution, or modification of this code, via any medium, is strictly prohibited. 

The source code is provided on GitHub for **portfolio review and educational purposes only**. 
If you wish to use any part of this project for commercial purposes or public distribution, please contact the author at d.kuzavkov@gmail.com.
