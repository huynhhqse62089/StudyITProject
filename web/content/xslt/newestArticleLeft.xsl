<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : newestArticle.xsl
    Created on : March 6, 2018, 11:40 AM
    Author     : yncdb
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" />
    <xsl:template match="/">
        <a class="a-for-art">
            <xsl:attribute name="href">ArticleDetail?articleId=<xsl:value-of select="./*[local-name()='id']" /></xsl:attribute>
            <img height="auto" width="100%">
                <xsl:attribute name="src">
                    <xsl:value-of select="//*[local-name()='thumbnail']" />
                </xsl:attribute>
                <xsl:attribute name="alt">
                    <xsl:value-of select="//*[local-name()='title']" />
                </xsl:attribute>
            </img>            
        </a>
        <h1 class="tieu-de-bai-viet-lon">
            <a class="a-for-art">
                <xsl:attribute name="href">ArticleDetail?articleId=<xsl:value-of select="./*[local-name()='id']" /></xsl:attribute>
                <xsl:attribute name="title">
                    <xsl:value-of select="//*[local-name()='title']" />
                </xsl:attribute>
                <xsl:value-of select="//*[local-name()='title']" />
            </a>            
        </h1>
        <p class="tieu-de-nho">
            <xsl:value-of select="//*[local-name()='pubDate']" />
        </p>
        <p class="noi-dung-bai-viet">
            <xsl:value-of select="//*[local-name()='summary']" />  
        </p>
    </xsl:template>
</xsl:stylesheet>
