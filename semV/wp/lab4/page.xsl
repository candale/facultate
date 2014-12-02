<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
      <head>
          <link rel="stylesheet" type="text/css" href="style.css" />
      </head>
    <body>
      <h2>Bibliography</h2>
      <table border="1">
        <tr bgcolor="#9acd32">
          <th>Title</th>
          <th>Publication Date</th>
          <th>Author</th>
        </tr>
        <xsl:for-each select="main/articles/article">
          <xsl:sort select="pub-year"/>
          <xsl:if test="pub-year &gt;= 2001 and pub-year &lt;= 2007">
            <tr>
              <td class='article'><xsl:value-of select="title"/></td>
              <td class='article'><xsl:value-of select="pub-year"/></td>
              <td class='article'><xsl:value-of select="author"/></td>
            </tr>
          </xsl:if>
        </xsl:for-each>
        <xsl:for-each select="main/book-reports/book-report">
          <xsl:sort select="pub-year"/>
          <xsl:if test="pub-year &gt;= 2001 and pub-year &lt;= 2007">
            <tr>
              <td class='book-report'><xsl:value-of select="title"/></td>
              <td class='book-report'><xsl:value-of select="pub-year"/></td>
              <td class='book-report'><xsl:value-of select="author"/></td>
            </tr>
          </xsl:if>
        </xsl:for-each>
        <xsl:for-each select="main/presentations/presentation">
          <xsl:sort select="pub-year"/>
          <xsl:if test="pub-year &gt;= 2001 and pub-year &lt;= 2007">
            <tr>
              <td class='presentation'><xsl:value-of select="title"/></td>
              <td class='presentation'><xsl:value-of select="pub-year"/></td>
              <td class='presentation'><xsl:value-of select="author"/></td>
            </tr>
          </xsl:if>
        </xsl:for-each>
        <xsl:for-each select="main/web-links/web-link">
          <xsl:sort select="pub-year"/>
          <xsl:if test="pub-year &gt;= 2001 and pub-year &lt;= 2007">
            <tr>
              <td class='web-link'><xsl:value-of select="author"/></td>
              <td class='web-link'><xsl:value-of select="pub-year"/></td>
            </tr>
          </xsl:if>
        </xsl:for-each>
        <xsl:for-each select="main/soft-packs/soft-pack">
          <xsl:sort select="pub-year"/>
          <xsl:if test="pub-year &gt;= 2001 and pub-year &lt;= 2007">
            <tr>
              <td class='soft-pack'><xsl:value-of select="author"/></td>
              <td class='soft-pack'><xsl:value-of select="pub-year"/></td>
            </tr>
          </xsl:if>
        </xsl:for-each>
      </table>
    </body>
    </html>
  </xsl:template>

</xsl:stylesheet>