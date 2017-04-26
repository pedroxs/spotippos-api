package config

trait BaseConfig {

    val injection: InjectionStep = rampUsers(10) over (10 seconds)

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate, sdch, br")
        .acceptLanguageHeader("en-US,en;q=0.8,pt-BR;q=0.6,pt;q=0.4,es;q=0.2")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")

    val headers_http = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

}
