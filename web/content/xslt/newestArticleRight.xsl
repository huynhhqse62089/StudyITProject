<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : newestArticleRight.xsl
    Created on : March 6, 2018, 2:33 PM
    Author     : yncdb
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <div class="detail">
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
        </div>
        <div class="noi-dung-detail">
            <h1 class="tieu-de-bai-viet">
                <a class="a-for-art">
                    <xsl:attribute name="href">ArticleDetail?articleId=<xsl:value-of select="./*[local-name()='id']" /></xsl:attribute>
                    <xsl:attribute name="title">
                        <xsl:value-of select="//*[local-name()='title']" />
                    </xsl:attribute>
                    <xsl:value-of select="//*[local-name()='title']" />
                </a>  
            </h1>
            <p class="tieu-de-nho-detail">
                <xsl:value-of select="//*[local-name()='pubDate']" />
            </p>
        </div>
    </xsl:template>
</xsl:stylesheet>
