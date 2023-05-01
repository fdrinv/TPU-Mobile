package com.fedorinov.tpumobile.ui.start.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fedorinov.tpumobile.R
import com.fedorinov.tpumobile.ui.theme.PADDING_BIG
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

@Composable
fun LogoCompanyBrand(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.tpu_logo),
            contentDescription = stringResource(R.string.tpu),
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.height(PADDING_BIG))
        Text(
             text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.logo_tomsk))
                }
                append(" ${stringResource(R.string.logo_polytech)}")
            },
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview
@Composable
private fun LogoCompanyBrandPreview() {
    TPUMobileTheme {
        LogoCompanyBrand()
    }
}