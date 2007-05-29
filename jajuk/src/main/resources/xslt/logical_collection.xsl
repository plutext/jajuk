<?xml version='1.0' encoding='UTF-8'?>

<!-- 
	Description: This XSLT transforms a xml file containing the tagging of the full logical collection into an html file.
	Author: The Jajuk Team
	Created: August 23, 2006
-->

<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform'
	version='1.0'>

	<xsl:template match='/'>
		<xsl:output method='xml' doctype-system='http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd' doctype-public='-//W3C//DTD XHTML 1.0 Strict//EN'/> 
		<html xmlns='http://www.w3.org/1999/xhtml'>
		<head>
				<meta http-equiv='Content-Type'
					content='text/html; charset=utf-8' />
				<title>Jajuk Music Report</title>
				<link rel='stylesheet' href='report-all.css' type='text/css' media='all'/>
				<link rel='stylesheet' href='report-print.css' type='text/css' media='print'/>
			</head>
			<body>
				<h1>
					<xsl:value-of
						select='/collection/i18n/ReportAction.1' />
				</h1>
			    			<p class='notice'>
						<xsl:value-of
							select='/collection/i18n/ReportAction.2' />
				</p>
					<ul class="jumpto">
					<li class='.jumpto li'><xsl:value-of
								select='/collection/i18n/ReportAction.19' /></li>
					<li><a href="#a1"><xsl:value-of
								select='/collection/i18n/ReportAction.7' /></a></li>
					<li><a href="#a2"><xsl:value-of
								select='/collection/i18n/ReportAction.8' /></a></li>
					<li><a href="#a2"><xsl:value-of
								select='/collection/i18n/ReportAction.9' /></a></li>
					<li><a href="#a2"><xsl:value-of
								select='/collection/i18n/ReportAction.18' /></a></li>
    			</ul>
				<h2 id='a1'>
					<xsl:value-of
						select='/collection/i18n/ReportAction.7' />
				</h2>
				<xsl:call-template name='styles' />
				<h2 id='a2'>
					<xsl:value-of
						select='/collection/i18n/ReportAction.8' />
				</h2>
				<xsl:call-template name='style-album' />
				<h2 id='a3'>
					<xsl:value-of
						select='/collection/i18n/ReportAction.9' />
				</h2>
					<xsl:call-template name='style-author-album' />
				<h2 id='a4'>
					<xsl:value-of
						select='/collection/i18n/ReportAction.18' />
				</h2>
				<xsl:call-template name='style-author-album-track' />
			</body>
		</html>
	</xsl:template>


	<xsl:template name='styles'>
		<table border='0' cellspacing='5'>
			<xsl:for-each select='/collection/style'>
				<tr>
					<xsl:variable name='id' select='id' />
					<td>
						<a href='#a{id}'>
							<xsl:value-of select='name' />
						</a>
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name='style-album'>
		<xsl:for-each select='/collection/style'>
			<xsl:variable name='id' select='id' />
			<h3 id='a{id}'>
				<xsl:value-of select='name' />
			</h3>
			<table border='0' cellspacing='5'>
					<tr>
						<th>
							<xsl:value-of
								select='/collection/i18n/ReportAction.name' />
						</th>
						<th>
							<xsl:value-of
								select='/collection/i18n/ReportAction.author' />
						</th>
						<th>
							<xsl:value-of
								select='/collection/i18n/ReportAction.year' />
						</th>

					</tr>
					<xsl:for-each select='album'>
						<tr>
							<td width='50%'>
								<xsl:value-of select='name' />
							</td>
							<td width='30%'>
								<xsl:value-of select='author' />
							</td>
							<td width='5%'>
								<xsl:value-of select='year' />
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</xsl:for-each>
	</xsl:template>

	<xsl:template name='style-author-album'>
			<xsl:for-each select='/collection/style'>
				<xsl:variable name='id' select='id' />
				<h3><xsl:value-of select='name' /></h3>
				<xsl:for-each select='author'>
					<h4><xsl:value-of select='name' /> </h4>
					<table border='0' cellspacing='5'>
						<tr>
							<th>
								<xsl:value-of
									select='/collection/i18n/ReportAction.name' />
							</th>
							<th>
								<xsl:value-of
									select='/collection/i18n/ReportAction.year' />
							</th>

						</tr>
						<xsl:for-each select='album'>
							<tr>
								<td>
									<xsl:value-of select='name' />
								</td>
								<td>
									<xsl:value-of select='year' />
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</xsl:for-each>
			</xsl:for-each>
	</xsl:template>
	
	<xsl:template name='style-author-album-track'>
		<xsl:for-each select='/collection/style'>
			<xsl:variable name='id' select='id' />
			<h3><xsl:value-of select='name' /></h3>
			<xsl:for-each select='author'>
				<h4><xsl:value-of select='name' /></h4>
				<xsl:for-each select='album'>
					<xsl:variable name='id' select='id' />
					<h5><xsl:value-of select='name' /> (<xsl:value-of select='year' />)</h5>
					<table border='0' cellspacing='5'>
						<tr>
							<th><xsl:value-of select='/collection/i18n/ReportAction.order'/></th>
							<th><xsl:value-of select='/collection/i18n/ReportAction.track'/></th>
							<th><xsl:value-of select='/collection/i18n/ReportAction.album'/></th>
							<th><xsl:value-of select='/collection/i18n/ReportAction.author'/></th>
							<th><xsl:value-of select='/collection/i18n/ReportAction.style'/></th>
							<th><xsl:value-of select='/collection/i18n/ReportAction.length'/></th>
							<th><xsl:value-of select='/collection/i18n/ReportAction.rate'/></th>
							<th><xsl:value-of select='/collection/i18n/ReportAction.comment'/></th>
						</tr>
						<xsl:for-each select='track'>
							<tr>
								<td width='5%'>
									<xsl:value-of select='order' />
								</td>
								<td width='20%'>
									<xsl:value-of select='name' />
								</td>
								<td width='10%'>
									<xsl:value-of select='album' />
								</td>
								<td width='10%'>
									<xsl:value-of select='author' />
								</td>
								<td width='10%'>
									<xsl:value-of select='style' />
								</td>
								<td width='10%'>
									<xsl:value-of select='length' />
								</td>
								<td width='5%'>
									<xsl:value-of select='rate' />
								</td>
								<td>
									<xsl:value-of select='comment' />
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>