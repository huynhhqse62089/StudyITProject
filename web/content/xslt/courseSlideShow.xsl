<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : courseSlideShow.xsl
    Created on : March 6, 2018, 9:53 PM
    Author     : yncdb
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <xsl:for-each select="//*[local-name()='course']">
            <li class="carousel__item">
                <img id="pic">
                    <xsl:attribute name="src">
                        <xsl:value-of select="./*[local-name()='thumbnail']" />
                    </xsl:attribute>
                </img>
            </li>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
