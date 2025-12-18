package com.example.pertemuan12.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.R
import com.example.pertemuan12.modeldata.DataSiswa
import com.example.pertemuan12.uicontroller.route.DestinasiHome
import com.example.pertemuan12.viewmodel.HomeViewModel
import com.example.pertemuan12.viewmodel.StatusUiSiswa
import com.example.pertemuan12.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_siswa)
                )
            }
        },
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.listSiswa,
            retryAction = { viewModel.loadSiswa() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: StatusUiSiswa,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is StatusUiSiswa.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is StatusUiSiswa.Success ->
            if (homeUiState.siswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.no_data))
                }
            } else {
                HomeBody(
                    listSiswa = homeUiState.siswa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        // onDetailClick(it.id)
                    }
                )
            }
        is StatusUiSiswa.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun HomeBody(
    listSiswa: List<DataSiswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (DataSiswa) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (listSiswa.isEmpty()) {
            Text(
                text = stringResource(R.string.no_data),
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            ListSiswa(
                listSiswa = listSiswa,
                modifier = Modifier.padding(horizontal = 14.dp),
                onDetailClick = { onDetailClick(it) }
            )
        }
    }
}

@Composable
fun ListSiswa(
    listSiswa: List<DataSiswa>,
    modifier: Modifier = Modifier,
    onDetailClick: (DataSiswa) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(listSiswa) { siswa ->
            SiswaCard(
                siswa = siswa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(siswa) }
            )
        }
    }
}

@Composable
fun SiswaCard(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = siswa.alamat,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = siswa.telpon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

// Tambahan wajib agar fungsi HomeStatus di atas tidak merah (karena tidak ada di gambar)
@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    // Implementasi Loading
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    // Implementasi Error
}