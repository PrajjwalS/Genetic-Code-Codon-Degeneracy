public class codon_map
{
    // an object of codon_map has a Character 3-D array to store CODON SEQ vs Correspoding AA 1 letter code
    //  U=0; C=1; A=2; G=3;
    char [][][] mapping = new char[4][4][4];
    codon_map()
    {
        mapping[0][0][0] = 'F';
        mapping[0][0][1] = 'F';
        mapping[0][0][2] = 'L';
        mapping[0][0][3] = 'L';
        mapping[0][1][0] = 'S';
        mapping[0][1][1] = 'S';
        mapping[0][1][2] = 'S';
        mapping[0][1][3] = 'S';
        mapping[0][2][0] = 'Y';
        mapping[0][2][1] = 'Y';
        mapping[0][2][2] = '*';
        mapping[0][2][3] = '*';
        mapping[0][3][0] = 'C';
        mapping[0][3][1] = 'C';
        mapping[0][3][2] = '*';
        mapping[0][3][3] = 'W';
        mapping[1][0][0] = 'L';
        mapping[1][0][1] = 'L';
        mapping[1][0][2] = 'L';
        mapping[1][0][3] = 'L';
        mapping[1][1][0] = 'P';
        mapping[1][1][1] = 'P';
        mapping[1][1][2] = 'P';
        mapping[1][1][3] = 'P';
        mapping[1][2][0] = 'H';
        mapping[1][2][1] = 'H';
        mapping[1][2][2] = 'G';
        mapping[1][2][3] = 'G';
        mapping[1][3][0] = 'R';
        mapping[1][3][1] = 'R';
        mapping[1][3][2] = 'R';
        mapping[1][3][3] = 'R';
        mapping[2][0][0] = 'I';
        mapping[2][0][1] = 'I';
        mapping[2][0][2] = 'I';
        mapping[2][0][3] = 'M';
        mapping[2][1][0] = 'T';
        mapping[2][1][1] = 'T';
        mapping[2][1][2] = 'T';
        mapping[2][1][3] = 'T';
        mapping[2][2][0] = 'N';
        mapping[2][2][1] = 'N';
        mapping[2][2][2] = 'K';
        mapping[2][2][3] = 'K';
        mapping[2][3][0] = 'S';
        mapping[2][3][1] = 'S';
        mapping[2][3][2] = 'R';
        mapping[2][3][3] = 'R';
        mapping[3][0][0] = 'V';
        mapping[3][0][1] = 'V';
        mapping[3][0][2] = 'V';
        mapping[3][0][3] = 'V';
        mapping[3][1][0] = 'A';
        mapping[3][1][1] = 'A';
        mapping[3][1][2] = 'A';
        mapping[3][1][3] = 'A';
        mapping[3][2][0] = 'B';
        mapping[3][2][1] = 'B';
        mapping[3][2][2] = 'Q';
        mapping[3][2][3] = 'Q';
        mapping[3][3][0] = 'G';
        mapping[3][3][1] = 'G';
        mapping[3][3][2] = 'G';
        mapping[3][3][3] = 'G';

    }

}

