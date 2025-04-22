### 🚧 **This project is currently under active development. Contributions and suggestions are welcome!**

# 🎶 Durin’s Misty Melody

An AI-powered Music Streaming Microservices Backend inspired by the richness of Middle-Earth. Built with Spring Boot, PostgreSQL, MongoDB, RabbitMQ, and Keycloak. Designed to be scalable, secure, and intelligent — just like the songs of the dwarves echoing in the Misty Mountains. 🏔️

---

## 🧱 Microservices Overview

| # | Service | Description |
|--|---------|-------------|
| 1 | 🚀 **api-gateway** | Routes all requests to backend services, handles logging, rate limiting, and path-based routing. |
| 2 | 👤 **user-service** | Manages user registration, login, roles (Listener, Artist, Admin), followers/following, and favorites. Secured with OAuth2 & Keycloak. Data stored in PostgreSQL. |
| 3 | 🎶 **music-service** | Handles music uploads, file storage (local/S3), AI metadata extraction, tagging, and lyrics. Metadata stored in MongoDB. |
| 4 | 📁 **playlist-service** | Create/edit/delete playlists, manage tracks, share public/private playlists, and support auto-generated ones. Data stored in PostgreSQL. |
| 5 | 📡 **streaming-service** | Streams audio files with resume/seek support, token-based anti-piracy, and logs user activity. Supports local/CDN/S3. |
| 6 | 🤖 **ai-recommendation-service** | Uses listening data from RabbitMQ to generate smart playlists with AI (Gemini/GPT). Uses Redis for caching and MongoDB for storing insights. |
| 7 | 📊 **analytics-service** | Consumes events (play, like, skip) and generates user insights and reports (daily/weekly). |
| 8 | 📬 **notification-service** | Sends system-generated notifications (playlist updates, new followers, daily recommendations, alerts). |
| 9 | 💬 **search-service** | Full-text search on songs, playlists, artists. Supports autocomplete, fuzzy search, filters, and auto-indexing via RabbitMQ. |

---

![5961303d-8488-47a3-9453-70923a340520](https://github.com/user-attachments/assets/a0cfeb21-6a5d-4316-a04d-ed9de754b2fd)



